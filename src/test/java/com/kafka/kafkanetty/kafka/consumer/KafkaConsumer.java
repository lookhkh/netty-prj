package com.kafka.kafkanetty.kafka.consumer;

import java.util.stream.IntStream;

public class KafkaConsumer {


	public IntStream poll(int num) {
		return IntStream.range(0, num);
	}
	
}
