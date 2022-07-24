package com.kt.onnuripay.message.kafka.xroshot.client.channelmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.kt.onnuripay.datavo.msg.MessageWrapper;
import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.kafka.parser.XMLParser;
import com.kt.onnuripay.message.kafka.xroshot.client.ClientBootStrap;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.init.SingleHandlerInit;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.SmsPushServerInfoVo;
import com.kt.onnuripay.message.util.LoggerUtils;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  요구사항
 *  1. Channel은 스프링 컨테이너가 기동 후, 자동으로 xroshot 서버와 인증을 시도한다.
 *      1.1 인증이 성공한다 -> 정상로직
 *      1.2 인증이 실패한다 -> 인증에 실패할 경우 에러로 처리한다.
 *  2. 생성된 채널은 x분 마다( idle 기간은 우선 임시로 처리하며 추후 xroshot 관리자와 협의하도록 한다.) 정의된 프로토콜에 따라 health-check를 지속적으로 수행한다.
 *      2.1 health-check가 지정된 프로토콜에 따라 실패할 경우, 생성된 channel을 close하며, 다시 인증을 재시도한다. 재시도 중에는 접근하는 스레드는 블락되어야만 한다. 
 *  3. 생성된 채널은 Thread-safe 해야만 한다.
 *  4. 생성된 채널에 단건, 동보, 대량 메시지 write가 가능해야만 한다. 
 * s
 * 
 * </p>
 */
@Component
@Slf4j
public class XroshotChannelManager {
    
    private final XroshotParameter param;
    private final ClientBootStrap bootStrap;
    private final XMLParser parser;
    private final SingleHandlerInit init;
    private final ScheduledExecutorService scheduler;
    
    
    private Channel xroshotChannel;
    
    
       
    public static final AttributeKey<String> KEY = AttributeKey.valueOf("status");
   
    public static final String REQ_SERVER_TIME = "req_server_time_completed";
    public static final String REQ_AUTH = "req_auth_completed";
    
    public XroshotChannelManager(XroshotParameter param, ClientBootStrap bootStrap, XMLParser parser, SingleHandlerInit init, @Qualifier("scheduler-thread")ScheduledExecutorService scheduler) {
        this.param = param;
        this.bootStrap = bootStrap;
        this.parser = parser;
        this.init = init;
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() {
        try {
            
            connectToXroshotServer();
            this.scheduler.scheduleWithFixedDelay(()->{
                if(XroshotChannelManager.isLoginSuccess(xroshotChannel)) xroshotChannel.write(XMLConstant.REQ_PING);
            }, 60, 60, TimeUnit.SECONDS);
                            
        } catch (IOException e) {
            
            e.printStackTrace();
            
        }
        
    }

    
    /**
     * 
     * @return Channel 크로샷 인증이 끝난 Channel을 리턴하며, SMS 발송 시, 해당 채널을 주입받아 공용으로 사용
     * @throws IOException
     * 
     * TODO 크로샷 서버와 로그인 완료 후, 메시지를 바로 보낼 수 있게끔 준비하여 Bean으로 등록한다. 크로샷 계약 완료 후 다시 구현
     * /**
     *          네티 클라이언트               Xroshot
     *          
     *          서버 시간 요청        ->>
     *                          <--         서버 시간 응답
     *          
     *          로그인 요청          ->> 
     *                          <---        로그인 응답 및 세션 생성
     *  
     *          메시지 전송      ->>     
     * 
     * 
     */
    public void connectToXroshotServer() throws IOException {

        StringBuffer temp = connectToXroshotControllerServerAndGetServerInfo();
                
        SmsPushServerInfoVo vo = this.parser.deserialzeFromJson(temp.toString(), SmsPushServerInfoVo.class);
        
        LoggerUtils.logDebug(log, "deserialzied result of server info => {}", vo); 
        
        this.xroshotChannel = bootStrap.start(init.getChannelInit(), vo.getResource().getAddress(), vo.getResource().getPort());
        
    }

    private StringBuffer connectToXroshotControllerServerAndGetServerInfo() throws MalformedURLException, IOException {
       
        URL url = new URL(this.param.getSendServerUrl());
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer temp = new StringBuffer();
        String t;
        
        while((t = r.readLine())!=null) {
            temp.append(t);
        }
                
        con.disconnect();
        r.close();
        
        return temp;
    }
    
    /**
     * 
     * @param vo
     * @param consume 메시지가 비동기 적으로 write 이벤트가 성공할 경우, 처리할 callback을 전달
     * @apiNote xroshotChannel이 writable 한 경우, 메시지를 비동기 적으로 전송한다.
     */
    public void send(MessageWrapper vo, Consumer<MessageWrapper> consume) {
        
        if(this.xroshotChannel.isActive() && XroshotChannelManager.isLoginSuccess(this.xroshotChannel)) {
            
            this.xroshotChannel.write(vo).addListener(future -> consume.accept(vo));
            
        }else {
            
            log.error(" failed to send {} because channel {} is close or session is not established => channel is active? {}, channel status is {}", 
                    vo, this.xroshotChannel, this.xroshotChannel.isActive(), this.xroshotChannel.attr(XroshotChannelManager.KEY));
            /**
             * TODO Channel이 준비되어 있지 않을 때, 처리할 로직 추가
             */
        }
        
    }
    
    public void closeChannel() {
        try {
            this.xroshotChannel.close().sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                public void operationComplete(Future<? super Void> future) throws Exception {
                    log.warn("{} close operation finished, current active status {}", xroshotChannel,xroshotChannel.isActive());              
                };
            });
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @param ctx xroshot 서버와 연결되어 있는 채널의 ctx를 받는다.
     * @return Xroshot 서버와 로그인 인증 과정이 끝난 경우 true를 반환한다.
     */
    public static boolean isLoginSuccess(Channel channel) {
        
        return channel.attr(XroshotChannelManager.KEY).get() == XroshotChannelManager.REQ_AUTH;
        
    }
    
    
    
}
