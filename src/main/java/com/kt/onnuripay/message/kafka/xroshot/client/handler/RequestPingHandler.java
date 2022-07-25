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
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.kt.onnuripay.message.common.exception.XroshotRuntimeException;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.PingResponse;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
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
    
    private final int interval = 5;
    private ScheduledFuture<?> schedulFuture ;
    
    private EventLoop loop;
    
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      
      log.info("{} is added",this);
      super.handlerAdded(ctx);
    }
  
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      if(msg instanceof PingResponse) {
          
          log.info("Ping Hanlder Received Ping Response from server {}",msg);
          setResponseTime(LocalTime.now());
          
      }else {
          super.channelRead(ctx, msg);
      }
    }
  
    
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      if(msg.equals(XMLConstant.REQ_PING)) {
          
          promise.setSuccess();
          
          log.info("Write Request Ping to server");
          

          Mas reqPing = Mas.builder()
                  .method(XMLConstant.REQ_PING)
                  .result(XMLConstant.OK)
                  .build();
          
          ctx.writeAndFlush(reqPing).addListener(new GenericFutureListener<Future<? super Void>>() {
              @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                
                  setSendTime(LocalTime.now());
                  setCount(count+1);
                  log.info("write PING To Server, sendTime => {}, responseTime => {}, currrent Cnt => {}",sendTime, responseTime, count);

                  if(count>3) {
                      log.error("PING 횟수 초과 에러 발생");
                      ctx.fireExceptionCaught(new XroshotRuntimeException("Exceeded 3 times for ping", reqPing));
                  }

            }
          });
          
          
      }else {
          super.write(ctx, msg, promise);
          
      }
      
    }
    
   
 
}
