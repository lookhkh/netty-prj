package com.kt.onnuripay.message.kafka.xroshot.client.handler;

import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestPingHandler extends ChannelOutboundHandlerAdapter {

  @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      
      log.info("{} is added",this);
      
        super.handlerAdded(ctx);
    }
    
  @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      if(msg.equals(XMLConstant.REQ_PING)) {
          log.info("Write Request Ping to server");
          Mas reqPing = Mas.builder()
                  .method(XMLConstant.REQ_PING)
                  .result(XMLConstant.OK)
                  .build();
          
          ctx.write(reqPing);
      }else {
          super.write(ctx, msg, promise);

      }
      
    }
    
 
}
