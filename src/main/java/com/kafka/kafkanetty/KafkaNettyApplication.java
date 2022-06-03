package com.kafka.kafkanetty;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kafka.kafkanetty.kafka.KafkaConsumerComponent;

@SpringBootApplication
public class KafkaNettyApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(KafkaNettyApplication.class, args);
	}

}
