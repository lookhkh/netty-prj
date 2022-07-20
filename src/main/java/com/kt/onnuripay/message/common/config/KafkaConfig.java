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
package com.kt.onnuripay.message.common.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.kt.onnuripay.message.kafka.controller.DispatcherController;
import com.kt.onnuripay.message.kafka.listener.AckMessageListener;


@Configuration
@EnableKafka
public class KafkaConfig {


	private final Environment env;
	
	private final DispatcherController controller;
	
	@Qualifier("single")
	private final ExecutorService exex;
	
	

	public KafkaConfig(Environment env, DispatcherController controller, @Qualifier("single") ExecutorService exex) {
		this.env = env;
		this.controller = controller;
		this.exex = exex;
	}


	@Bean(name = "kafkaSingleListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaContainerFactory(){
		
		Map<String, Object> config = createConfig();

		ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(config);
		
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(consumerFactory);
		factory.setConcurrency(1);
		
		factory.getContainerProperties().setMessageListener(ackMessageListener());
		factory.getContainerProperties().setPollTimeout(1000);
		
		return factory;
		
	}



	@Bean(name = "kafkaBatchListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaBatchListenerContainerFactory(){
		
		Map<String,Object> config = createConfig();
		config.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 10000);//fetch_min_byte, 최소로 가져오는 바이트 크기
		config.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 10_000); //fetch_min_byte가 충족되지 않을 경우 최대 대기시간.
		
		ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(config);
		
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(consumerFactory);
		factory.setConcurrency(1);
		factory.setBatchListener(true);
		
		factory.getContainerProperties().setMessageListener(ackMessageListener());
		factory.getContainerProperties().setPollTimeout(10000);
		
		return factory;
		
	}
	
	private Map<String, Object> createConfig() {
		Map<String,Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);

		return config;
	}
	
	
	
	

	public AckMessageListener ackMessageListener() {
		return new AckMessageListener(controller,exex);
	}
	
	
	

	
	
}
