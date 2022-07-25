package com.kt.onnuripay.message.kafka.xroshot.client.handler;

import java.time.Duration;

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

import com.google.api.client.util.DateTime.SecondsAndNanos;
import com.kt.onnuripay.message.common.exception.XroshotRuntimeException;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.PingResponse;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
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
    
    private final ScheduledExecutorService scheduler;
    

    public RequestPingHandler(ScheduledExecutorService scheduler) {
        super();
        this.scheduler = scheduler;
    }
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      
      log.info("{} is added",this);
      super.handlerAdded(ctx);
    }
  
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      if(msg instanceof PingResponse) {
          setResponseTime(LocalTime.now());
          
          log.info("Ping Hanlder Received Ping Response from server {}, last send time {}, response time {}",msg, sendTime, responseTime);
                    
          long diff = responseTime.getSecond() - sendTime.getSecond();
                   
          if(diff <= 5) {
              log.info("5초 이내에 response 도착 result => valid");
              cleanup();
          }else {
              log.error("response 도착 5초 초과, result => invalid, 재시도 | 현재 시도 횟수 : {}",count);
              
          }
          
      }else {
          super.channelRead(ctx, msg);
      }
    }

    private void cleanup() {
          this.setCount(0);
          this.setResponseTime(null);
          this.setSendTime(null);
          schedulFuture.cancel(false);
          
          log.info(" cleanup all state in the handler ===> count {}, responseTime {},sendTime {} set null ,,,, {}",count,responseTime, sendTime,schedulFuture);
          
    }
  
    
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      if(msg.equals(XMLConstant.REQ_PING)) {
          
          log.info(" Schduler start to work ");

          schedulFuture = scheduler.scheduleWithFixedDelay(()->startPingSchduler(ctx,promise), 0, interval, TimeUnit.SECONDS);

      }else {
          super.write(ctx, msg, promise);
          
      }
      
    }

    private void startPingSchduler(ChannelHandlerContext ctx,ChannelPromise promise) {

        Mas reqPing = Mas.builder()
                  .method(XMLConstant.REQ_PING)
                  .result(XMLConstant.OK)
                  .build();
          
          ctx.writeAndFlush(reqPing)
             .addListener(new GenericFutureListener<Future<? super Void>>() {
              @Override
              public void operationComplete(Future<? super Void> future) throws Exception {

                  setSendTime(LocalTime.now());
                  setCount(count+1);
      
                  if(count>3) {
                      log.error("PING 횟수 초과 에러 발생");
                      ctx.fireExceptionCaught(new XroshotRuntimeException("Exceeded 3 times for ping"));
                  }
            }
          });
    }

    
   
 
}
