package com.kafka.kafkanetty.client.test.manager;

import java.util.Random;

import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.ping.PingResponse;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestPingResponseHanlder extends ChannelOutboundHandlerAdapter {

    int cnt = 0;

  
    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        cnt++;
        cnt = cnt%4;
        
        PingResponse r = new PingResponse(XMLConstant.RES_PING, XMLConstant.OK);
        
        Thread.sleep(new Random().nextLong(1000, 9000));

        ctx.fireChannelRead(r);
        
    }

}
