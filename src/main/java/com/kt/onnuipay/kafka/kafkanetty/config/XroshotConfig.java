package com.kt.onnuipay.kafka.kafkanetty.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.Dsl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.kt.onnuipay.client.ClientBootStrap;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.impl.ParsingServerResponse;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.impl.PrepareAndStartNettyClient;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.SmsPushServerInfoVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * KT OnnuriPay version 1.0
 *
 *  Copyright ⓒ 2022 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */


/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 6. 29.
 * @apiNote XroshotConfig. 비동기 기반 AsyncHttpClient 생성 
 */

@Configuration
@EnableConfigurationProperties(value= {XroshotParameter.class})
@PropertySource("classpath:xroshot.properties")
@AllArgsConstructor
@Slf4j
public class XroshotConfig {

	@Qualifier("netty-event-group")
	private final EventLoopGroup loop;
	
	private final XMLParser parser;
	
	private final ClientBootStrap boot;
	
	private final PrepareAndStartNettyClient init;

	
	/**
	 * 
	 * @return Channel 크로샷 인증이 끝난 Channel을 리턴하며, SMS 발송 시, 해당 채널을 주입받아 공용으로 사용
	 * @throws IOException
	 * 
	 * TODO 크로샷 서버와 로그인 완료 후, 메시지를 바로 보낼 수 있게끔 준비하여 Bean으로 등록한다.
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
	@Bean("Xroshot-channel")
	public Channel getChannelPool() throws IOException {
	    

	    URL url = new URL("https://jsonplaceholder.typicode.com/todos/1");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer temp = new StringBuffer();
        String t;
        
        while((t = r.readLine())!=null) {
            temp.append(t);
        }
        
        log.info("Xroshot init info result => {}",temp.toString());
        
        con.disconnect();
        r.close();
        
//        SmsPushServerInfoVo vo = parser.deserialzeFromJson(temp.toString(), SmsPushServerInfoVo.class);
//        
//        return boot.start(init.execute(vo), vo.getResource().getAddress(), vo.getResource().getPort());
//           
        
	    /**
	     * TODO 크로샷 연동 규격 확인.
	     * 1. 크로샷의 각각의 인스턴스와 연동이 가능한지? 혹은 하나의 SP ID 당 하나의 인스턴스와만 Connection을 맺을 수 있는 것인지?
	     * 220711 조현일
	     */


//        
//	    
//	    ChannelPoolMap<InetSocketAddress, FixedChannelPool> channelPoolMap = new AbstractChannelPoolMap<InetSocketAddress, FixedChannelPool>() {
//            @Override
//            protected FixedChannelPool newPool(InetSocketAddress key) {
//                return new FixedChannelPool(bootStrap.remoteAddress(key), new AbstractChannelPoolHandler() {
//                    @Override
//                    public void channelCreated(Channel ch) throws Exception {
//                        
//                    }
//                }, 1);
//            }
//        };
        
        return null;
	}
		
	
	/****
	 * 
	 * TODO AsyncHttpClient 자원관리 신경 써야 한다. 220629 조현일
	 */
	
	@Bean
	public AsyncHttpClient getAsyncHttpClient() {
		
		AsyncHttpClientConfig config = Dsl.config().setEventLoopGroup(this.loop).setRequestTimeout(30000).setCompressionEnforced(true).build();
		AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient(config);
		return asyncHttpClient;
	}
	

	
	
}
