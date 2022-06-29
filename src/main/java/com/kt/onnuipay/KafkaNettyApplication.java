package com.kt.onnuipay;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.asynchttpclient.AsyncHttpClient;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
/**
 * @see Thread-worker 개수 성능 테스트에 따라 조절 필요. 현재는 single - db_connection_pool에 맞춰 테스트
 * 
 * **/
@SpringBootApplication
public class KafkaNettyApplication {

	
	
	public static void main(String[] args) throws InterruptedException, BeansException, IOException {
		ConfigurableApplicationContext  ctx = SpringApplication.run(KafkaNettyApplication.class, args);
		XroshotParameter param = ctx.getBean(XroshotParameter.class);
		


	}

	
	/**
	 * TODO 스레드플 개수 지정 220616 조현일
	 * - 데이터 처리 로직은 싱글쓰레드가 담당
	 * **/
	@Bean("single")
	public ExecutorService getDefault() {
		ExecutorService service =  Executors.newSingleThreadExecutor(new ThreadFactory() {
			int cnt = 0;
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName("Thread-Wokrer-Push_Server "+cnt);
				cnt++;
				return t;
			}
		});
		
		return service;
		
	}
	
	/**
	 * TODO 스레드플 개수 지정 220616 조현일
	 * - 데이터 처리 로직은 싱글쓰레드가 담당
	 * **/
	@Bean("db-thread-pool")
	public ExecutorService getDbHandlingThreadPoolDefault() {
		ExecutorService service =  Executors.newFixedThreadPool(10,new ThreadFactory() {
			int cnt = 0;
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName("Thread-Wokrer-DB "+cnt);
				cnt++;
				return t;
			}
		});
		
		return service;
		
	}
	

	
	
	
}
