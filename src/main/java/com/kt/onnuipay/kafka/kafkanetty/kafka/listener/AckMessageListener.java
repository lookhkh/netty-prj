package com.kt.onnuipay.kafka.kafkanetty.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherController;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;

import lombok.AllArgsConstructor;
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


@AllArgsConstructor
@Component
@Slf4j
public class AckMessageListener  {
	
	private final DispatcherController dispatch;

	@KafkaListener(topics = "hello.kafka", groupId = "spring-boot", containerFactory = "kafkaListenerContainerFactory")
	public void listen(@Payload String msg) {
		log.warn("{} came from broker"+msg);
		ResultOfPush result =  dispatch.route(msg);
		log.warn("Push result => {}",result);
	}
}
