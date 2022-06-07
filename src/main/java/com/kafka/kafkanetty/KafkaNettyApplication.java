package com.kafka.kafkanetty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.kafka.kafkanetty.kafka.KafkaConsumerComponent;

@SpringBootApplication
public class KafkaNettyApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(KafkaNettyApplication.class, args);
		ctx.getBean("kafka-consumer",KafkaConsumerComponent.class).start();
		Thread.sleep(30000);
	}

}
