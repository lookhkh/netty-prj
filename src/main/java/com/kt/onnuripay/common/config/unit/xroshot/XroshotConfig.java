/*
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
package com.kt.onnuripay.common.config.unit.xroshot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.Dsl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.kt.onnuripay.common.config.vo.XroshotParameter;
import com.kt.onnuripay.kafka.parser.XMLParser;
import com.kt.onnuripay.kafka.xroshot.client.ClientBootStrap;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
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
        
        // 크로샷 계약 진행 후 마무리 -> 목표 : 단일 채널 생성 후, 전역에서 write event 발생 시 사용할 수 있도록 bean으로 관리 220715 조현일
//        SmsPushServerInfoVo vo = parser.deserialzeFromJson(temp.toString(), SmsPushServerInfoVo.class);
//        
//        return boot.start(init.execute(vo), vo.getResource().getAddress(), vo.getResource().getPort());
//           
        
	    /**
	     * TODO 크로샷 연동 규격 확인.
	     * 1. 크로샷의 각각의 인스턴스와 연동이 가능한지? 혹은 하나의 SP ID 당 하나의 인스턴스와만 Connection을 맺을 수 있는 것인지?
	     * 220711 조현일
	     */


        
        return null;
	}
		
	
	/****
	 * 
	 * TODO AsyncHttpClient 자원관리 신경 써야 한다. 220629 조현일
	 * TODO 동기식으로 크로샷 서버정보를 가져오는 방식으로 로직을 변경할 예정이기에 삭제 예정 
	 */
	
	@Deprecated
	@Bean
	public AsyncHttpClient getAsyncHttpClient() {
		
		AsyncHttpClientConfig config = Dsl.config().setEventLoopGroup(this.loop).setRequestTimeout(30000).setCompressionEnforced(true).build();
		AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient(config);
		return asyncHttpClient;
	}
	

	
	
}
