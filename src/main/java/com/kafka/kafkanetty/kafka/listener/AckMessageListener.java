package com.kafka.kafkanetty.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kafka.kafkanetty.kafka.DispatcherController;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
