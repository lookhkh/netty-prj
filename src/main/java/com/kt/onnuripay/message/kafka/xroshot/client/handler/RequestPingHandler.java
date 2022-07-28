package com.kt.onnuripay.message.kafka.xroshot.client.handler;

/**
 * MessageServer - Xroshot 
 * Ping Protocol
 * 
 * 1. 60초에 한 번 씩 SP는 Xroshot에 PING을 보낸다
 * 2. 5초 이내에 핑 응답이 오지 않을 경우 추가로 2번까지 더 PING을 보낸다.
 * 3. PING이 전부 실패할 경우, 연결을 끊고 재연결을 시도한다.
 * 
 * 
 */

import java.time.LocalTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.kt.onnuripay.message.common.exception.XroshotRuntimeException;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.ping.PingResponse;
import com.kt.onnuripay.message.util.LoggerUtils;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class RequestPingHandler extends ChannelDuplexHandler {

    private int count;
    private LocalTime sendTime;
    private LocalTime responseTime;
    
    private final long interval = 5;
  
    private ScheduledFuture<?> schedulFuture ;
    private ScheduledFuture<?> sendAtIntervalOf5Second;

    
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
        EventLoop loop = ctx.channel().eventLoop();
        
        schedulFuture = loop
                            .scheduleWithFixedDelay(
                                    ()->{
                                        sendAtIntervalOf5Second = loop
                                                .scheduleWithFixedDelay(() -> {
                                                    
                                                   if(!sendAtIntervalOf5Second.isCancelled()) startPingSchduler(ctx);
                                                    
                                                }, 0 ,5, TimeUnit.SECONDS);
                                        
                                    }, 0, 60, TimeUnit.SECONDS);
        LoggerUtils.logDebug(log, "PingRequestHandler active and start Ping schdule", "");

        super.channelActive(ctx);
        }
    

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      if(msg instanceof PingResponse) {
         
          checkPingCount(ctx);
          
          setResponseTime(LocalTime.now());
          
          long diff = responseTime.getSecond() - sendTime.getSecond();

          
          LoggerUtils.logDebug(log, "Ping Hanlder Received Ping Response from server {}, last send time {}, response time {}, dif {}", msg, sendTime, responseTime,diff);
          
                   
          if(diff <= 5) {
              LoggerUtils.logDebug(log,"5초 이내에 response 도착 result => valid");
              initStatusOnPingSuccess();
          }else {
              log.warn("response 도착 5초 초과, result => invalid, 재시도 | 현재 시도 횟수 : {}",count);
              
          }
          
      }else {
          super.channelRead(ctx, msg);
      }
    }
    
    private void initStatusOnPingSuccess() {
        this.setCount(0);
        this.setResponseTime(null);
        this.setSendTime(null);
        this.sendAtIntervalOf5Second.cancel(false);
        LoggerUtils.logDebug(log, "ping success and cleanup all state in the handler  ===> count {}, responseTime {},sendTime {} set null ,,,, {}", count,responseTime, sendTime,schedulFuture);

              
  }

    
    private void startPingSchduler(ChannelHandlerContext ctx) {

        Mas reqPing = Mas.builder()
                  .method(XMLConstant.REQ_PING)
                  .result(XMLConstant.OK)
                  .build();
        
        setSendTime(LocalTime.now());
        setCount(count+1);
          
          ctx.writeAndFlush(reqPing)
             .addListener(new GenericFutureListener<Future<? super Void>>() {
              @Override
              public void operationComplete(Future<? super Void> future) throws Exception {

                  checkPingCount(ctx);
            }

          });
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Error happend | Message => {}, and ping request handler clean up all resources",cause.getMessage());
        removeAllTaskFromScheduler();
        super.exceptionCaught(ctx, cause);
    }
    
    private void checkPingCount(ChannelHandlerContext ctx) {
        if(count>4) {
              removeAllTaskFromScheduler();
              log.warn("Ping Failed and cleanUp all PingRequestHandler Resource || status => {},{}",schedulFuture,sendAtIntervalOf5Second);
              ctx.fireExceptionCaught(new XroshotRuntimeException("Exceeded 3 times for ping"));
          }
    }
    
    private void removeAllTaskFromScheduler() {
        sendAtIntervalOf5Second.cancel(false);
        schedulFuture.cancel(false);
    }
}
