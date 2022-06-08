package com.kafka.kafkanetty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KafkaNettyApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(KafkaNettyApplication.class, args);
	}

}
