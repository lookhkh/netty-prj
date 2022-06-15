package com.kt.onnuipay.kafka.kafkanetty.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherController;
import com.kt.onnuipay.kafka.kafkanetty.kafka.listener.AckMessageListener;

import lombok.AllArgsConstructor;


/*
 * TODO KAFKA Consumer 설정이 반영이 안되고 있는 듯 한데 처리해야할 필요
 * TODO 단건 PUSH 처리 시, 쓰레드풀 크기 조절 필요 현재는 100 220610 조현일
 * 
 * */


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
@Configuration
@EnableKafka
public class KafkaConfig {


	@Autowired
	private final Environment env;
	
	@Autowired
	private final DispatcherController controller;
	
	@Bean("single")
	public ExecutorService getDefault() {
		ExecutorService service =  Executors.newFixedThreadPool(100);
		return service;
		
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaContainerFactory(){
		
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(1);
		
		factory.getContainerProperties().setMessageListener(ackMessageListener());
		factory.getContainerProperties().setPollTimeout(1000);
		
		return factory;
		
		
	}
	
	


	public AckMessageListener ackMessageListener() {
		return new AckMessageListener(controller);
	}
	
	
	

	public ConsumerFactory<? super String, ? super String> consumerFactory() {
		
		Map<String,Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		return new DefaultKafkaConsumerFactory<>(config);
	}
	
	
	
}
