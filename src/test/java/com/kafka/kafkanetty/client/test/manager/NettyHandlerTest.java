package com.kafka.kafkanetty.client.test.manager;

import static org.mockito.Mockito.mock;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.kafka.parser.XMLParser;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.ExceptionHospitalHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.RequestPingHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.codec.DefaultMessageToByteEncoder;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.codec.MessageDecoderTo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import util.XroshotTestUtil;

public class NettyHandlerTest {

    XMLParser parser = mock(XMLParser.class);
    
    XroshotParameter param = XroshotTestUtil.param;

    XMLParser realParserMapper = XroshotTestUtil.getParser();
    
    
    @Test
    @DisplayName("Xroshot Ping Hanlder Test")
    public void test_ping() throws InterruptedException {
       EmbeddedChannel ch = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG)
                , new MessageDecoderTo(realParserMapper) 
                , new DefaultMessageToByteEncoder(realParserMapper)
                , new RequestPingHandler()
                , new ExceptionHospitalHandler()
                );
       
        
       Mas reqPing = Mas.builder()
               .method(XMLConstant.REQ_PING)
               .result(XMLConstant.OK)
               .build();
        ch.eventLoop().scheduleWithFixedDelay(()->System.out.println("hi"), 0, 1, TimeUnit.SECONDS);
        Thread.sleep(10);
    }

}
