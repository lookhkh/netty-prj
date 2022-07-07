package com.kt.onnuipay;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
import org.springframework.core.env.Environment;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import datavo.MetaData;
import datavo.enums.KafkaKeyEnum;
import datavo.enums.MsgType;
import datavo.enums.TypeOfSending;
import datavo.msg.MessageWrapper;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
/**
 * @see Thread-worker 개수 성능 테스트에 따라 조절 필요. 현재는 single - db_connection_pool에 맞춰 테스트
 * 
 * **/
@SpringBootApplication
public class KafkaNettyApplication {

	 	@Autowired
	    Environment env;

	 	
	public static void main(String[] args) throws InterruptedException, BeansException, IOException {
		ConfigurableApplicationContext  ctx = SpringApplication.run(KafkaNettyApplication.class, args);
		
		

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
	
	/**
	 * 
	 * 
	 * TODO Netty & AsyncClient가 함게 사용하는 EventLoop, 개수 지정 최적화 필요 조현일 220701
	 * 
	 * **/
	@Bean("netty-event-group")
	public EventLoopGroup getDefaultNioEventLoopGroup() {
		
		int threads = Integer.valueOf(env.getProperty("netty.client.loopgroup.threads"));
		
		return new NioEventLoopGroup(threads);
	}
	

	
	
	
}
