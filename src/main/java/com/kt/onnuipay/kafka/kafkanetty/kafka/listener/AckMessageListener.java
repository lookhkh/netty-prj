package com.kt.onnuipay.kafka.kafkanetty.kafka.listener;

import java.util.List;
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



	@KafkaListener(topics = "single", groupId = "spring-boot-single", containerFactory = "kafkaSingleListenerContainerFactory")
	public void listen_single(@Payload String msg) {
		log.info("{} came from broker {}",msg, Thread.currentThread().getName());
		//service.submit(()->dispatch.route(msg));

	}
	
	@KafkaListener(topics = "batch", groupId = "spring-boot-batch", containerFactory = "kafkaBatchListenerContainerFactory")
	public void listen_batch(@Payload List<String> msg) {
		log.info("{} came from broker {}",msg, Thread.currentThread().getName());
		//service.submit(()->dispatch.route(msg));

	}
}
