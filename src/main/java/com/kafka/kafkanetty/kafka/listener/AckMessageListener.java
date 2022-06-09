package com.kafka.kafkanetty.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kafka.kafkanetty.kafka.DispatcherController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AckMessageListener  {
	
	private final DispatcherController dispatch;

	@KafkaListener(topics = "hello.kafka", groupId = "spring-boot", containerFactory = "kafkaListenerContainerFactory")
	public void listen(@Payload String msg) {
		System.out.println(msg+" from partition ");
		dispatch.route(msg);
	}
}
