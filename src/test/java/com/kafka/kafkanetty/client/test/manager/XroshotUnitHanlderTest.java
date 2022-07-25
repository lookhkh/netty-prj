package com.kafka.kafkanetty.client.test.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.kafka.parser.XMLParser;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.ExceptionHospitalHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.RequestPingHandler;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.PingResponse;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import util.XroshotTestUtil;

@Slf4j
public class XroshotUnitHanlderTest {

    
    XroshotParameter param = XroshotTestUtil.param;
    XmlMapper realParser;
    XMLParser realParserMapper;
    ScheduledExecutorService e = Executors.newScheduledThreadPool(1,new ThreadFactory() {
        
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("스케줄러-쓰레드");
            return t;
        }
    });
    
    
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
     * 1. 60초에 한 번 씩 SP는 Xroshot에 PING을 보낸다
     * 2. 5초 이내에 핑 응답이 오지 않을 경우 추가로 2번까지 더 PING을 보낸다.
     * 3. PING이 전부 실패할 경우, 연결을 끊고 재연결을 시도한다.
     * @throws InterruptedException 
     * 
     * 
     */
    
    //@Test
    public void t() throws InterruptedException {
        
        EmbeddedChannel ch = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG)
                , new RequestPingHandler()
                , new ExceptionHospitalHandler()
                    );
        
        
        e.scheduleAtFixedRate(()->{
            ch.write(XMLConstant.REQ_PING);
            
           Mas msg = ((Mas) ch.readOutbound());
           log.info("OUT - BOUND MESSAGE => {}",msg);
           
           PingResponse r= new PingResponse(XMLConstant.RES_PING, XMLConstant.OK);
           ch.writeInbound(r);
           
            
        }, 0, 8, TimeUnit.SECONDS);
        
        Thread.sleep(10_000_000);
    }
    
}
