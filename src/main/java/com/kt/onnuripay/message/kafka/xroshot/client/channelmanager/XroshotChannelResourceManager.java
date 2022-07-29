package com.kt.onnuripay.message.kafka.xroshot.client.channelmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.kafka.parser.XMLParser;
import com.kt.onnuripay.message.kafka.xroshot.client.ClientBootStrap;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.init.SingleHandlerInit;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.serverInfo.SmsPushServerInfoVo;
import com.kt.onnuripay.message.util.LoggerUtils;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class XroshotChannelResourceManager {

    private final XroshotParameter param;
    private final ClientBootStrap bootStrap;
    private final XMLParser parser;
    private final SingleHandlerInit init;
    
    
    private final XroshotInfoCache<String> cache;
    
    
    public XroshotChannelResourceManager(XroshotParameter param, ClientBootStrap bootStrap, XMLParser parser,
            SingleHandlerInit init) {
        this.param = param;
        this.bootStrap = bootStrap;
        this.parser = parser;
        this.init = init;
        
        this.cache = new XroshotInfoCache<>(30);
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
    public Channel connectToXroshotServer() {

            /**
             * TODO 서버정보 캐시 기능 추가 고려해보기 220728 조현일
             */
                    
            SmsPushServerInfoVo vo = this.parser.deserialzeFromJson(connectToXroshotControllerServerAndGetServerInfo(), SmsPushServerInfoVo.class);
            
            LoggerUtils.logDebug(log, "deserialzied result of server info => {}", vo); 
            
            return bootStrap.start(init.getChannelInit(), vo.getResource().getAddress(), vo.getResource().getPort());
       
        
    }
    
    /**
     * 
     * @return
     * @throws RuntimeException IO관련 에러가 발생할 시, RuntimeException으로 wrapping 한 이후, 에러를 발생시킨다.
     * @apiNote Xroshot Controller 서버로 접속하여 메시징 서버 접속 정보를 받아온다. Cache를 사용하여 IO를 줄일 수 있도록 한다.
     * 
     * 
     */
    private String connectToXroshotControllerServerAndGetServerInfo() throws RuntimeException {
        
        if(cache.isAvailable()) return cache.getCachedData();
        
        try {      
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

            return cache.put(temp.toString());
        
        }catch(MalformedURLException e) {
            throw new RuntimeException("MalformedURL Exception Happend while trying to connect to Xroshot Controller Server",e);
        }catch(IOException e) {
            throw new RuntimeException("UnKnown IOException Happend",e);
        }
        
        }


    
}
