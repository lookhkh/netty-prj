package com.kt.onnuipay.kafka.kafkanetty.kafka.listener;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherController;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;

import lombok.extern.slf4j.Slf4j;

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



@Component
@Slf4j
public class AckMessageListener  {
	
	private final DispatcherController dispatch;
	private final ExecutorService service;

	
	
	public AckMessageListener(DispatcherController dispatch, @Qualifier("single") ExecutorService service) {
		this.dispatch = dispatch;
		this.service = service;
	}



	@KafkaListener(topics = "hello.kafka", groupId = "spring-boot", containerFactory = "kafkaListenerContainerFactory")
	public void listen(@Payload String msg) {
		log.info("{} came from broker"+msg);
		service.submit(()->dispatch.route(msg));

	}
}
