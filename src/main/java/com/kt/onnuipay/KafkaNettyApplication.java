package com.kt.onnuipay;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
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

import com.kt.onnuipay.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;

@SpringBootApplication
public class KafkaNettyApplication {

	
	
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(KafkaNettyApplication.class, args);
	}

	
	/**
	 * TODO 스레드플 개수 지정 220616 조현일
	 * 
	 * **/
	@Bean("single")
	public ExecutorService getDefault() {
		ExecutorService service =  Executors.newFixedThreadPool(100,new ThreadFactory() {
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
	
}
