package com.kt.onnuipay.kafka.kafkanetty.config;

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
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;

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
	
	/****
	 * 
	 * TODO AsyncHttpClient 자원관리 신경 써야 한다. 220629 조현일
	 */
	
	@Bean
	public AsyncHttpClient getAsyncHttpClient() {
		
		AsyncHttpClientConfig config = Dsl.config().setEventLoopGroup(this.loop).build();
		AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient(config);
		return asyncHttpClient;

	}
	
	@Bean
	public XmlMapper getMapper() {
		
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);

		
		XmlMapper xmlMapper = new XmlMapper(module);
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

		return new XmlMapper();
	}
	
	
}
