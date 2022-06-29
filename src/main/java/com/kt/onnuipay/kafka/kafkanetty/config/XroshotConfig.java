package com.kt.onnuipay.kafka.kafkanetty.config;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.Dsl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;

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
public class XroshotConfig {

	
	/****
	 * 
	 * TODO AsyncHttpClient 자원관리 신경 써야 한다. 220629 조현일
	 */
	
	@Bean
	public AsyncHttpClient getAsyncHttpClient() {
		
		
		AsyncHttpClientConfig config = Dsl.config().build();
		AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient(config);
		return asyncHttpClient;

	}
	
	@Bean
	public XmlMapper getMapper() {
		return new XmlMapper();
	}
	
	
}
