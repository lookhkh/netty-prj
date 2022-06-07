package com.kafka.kafkanetty.kafka;

import org.springframework.stereotype.Component;

import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("kafka-consumer")
public class KafkaConsumerComponent {

	private final EventLoopGroup loop;
	
	public void start() {
		System.out.println(loop);
	}
}
