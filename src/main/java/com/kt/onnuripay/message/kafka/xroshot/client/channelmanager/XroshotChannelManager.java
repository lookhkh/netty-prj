package com.kt.onnuripay.message.kafka.xroshot.client.channelmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.kafka.parser.XMLParser;
import com.kt.onnuripay.message.kafka.xroshot.client.ClientBootStrap;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.DefaultChannelHandlerListener;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.init.SingleHandlerInit;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.SmsPushServerInfoVo;
import com.kt.onnuripay.message.util.LoggerUtils;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
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
    
    private Channel xroshotChannel;
    
    public static final AttributeKey<String> KEY = AttributeKey.valueOf("status");
   
    public static final String REQ_SERVER_TIME = "req_server_time_completed";
    public static final String REQ_AUTH = "req_auth_completed";
    
    public XroshotChannelManager(XroshotParameter param, ClientBootStrap bootStrap, XMLParser parser, SingleHandlerInit init) {
        this.param = param;
        this.bootStrap = bootStrap;
        this.parser = parser;
        this.init = init;
    }

    @PostConstruct
    public void init() {
        try {
            
            connectToXroshotServer();
       
            
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
                
        SmsPushServerInfoVo vo = this.parser.deserialzeFromJson(temp.toString(), SmsPushServerInfoVo.class);
        
        LoggerUtils.logDebug(log, "deserialzied result of server info => {}", vo); 
        
        this.xroshotChannel = bootStrap.start(init.getChannelInit(), vo.getResource().getAddress(), vo.getResource().getPort());
        
    }
    
    
    
    
}
