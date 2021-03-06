package com.kafka.kafkanetty.client.test.manager.handler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.processor.MasterDetailRecord;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.kafka.kafkanetty.client.test.manager.TestPingResponseHanlder;
import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.kafka.parser.XMLParser;
import com.kt.onnuripay.message.kafka.xroshot.client.channelmanager.XroshotChannelManager;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.ExceptionHospitalHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.RequestPingHandler;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.ping.PingResponse;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import util.XroshotTestUtil;

@Slf4j
public class XroshotUnitHanlderTest {

    
    XroshotChannelManager manager = Mockito.mock(XroshotChannelManager.class);
    
    XroshotParameter param = XroshotTestUtil.param;
    XmlMapper realParser;
    XMLParser realParserMapper;
    
    
    
    @BeforeEach
    public void init() {

            
            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);
            XmlMapper xmlMapper = new XmlMapper(module);
            xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
            
            this.realParser =  xmlMapper;
            this.realParserMapper  = new XMLParser(realParser);
    }
    
    
    /**
     * MessageServer - Xroshot 
     * Ping Protocol
     * 
     * 1. 60?????? ??? ??? ??? SP??? Xroshot??? PING??? ?????????
     * 2. 5??? ????????? ??? ????????? ?????? ?????? ?????? ????????? 2????????? ??? PING??? ?????????.
     * 3. PING??? ?????? ????????? ??????, ????????? ?????? ???????????? ????????????.
     * @throws InterruptedException 
     * 
     * 
     */
    
    @Test
    public void t() throws InterruptedException {
        
        EmbeddedChannel ch = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG)
                , new TestPingResponseHanlder()
                , new RequestPingHandler()
                    );

        Thread.sleep(10_000_000);
        
      
     
    }
    
}
