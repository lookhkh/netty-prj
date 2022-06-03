package com.kafka.kafkanetty.kafka.consumer.test;

import org.junit.jupiter.api.Test;

import com.kafka.kafkanetty.client.request.wrapper.ClientRequestWrapper;
import com.kafka.kafkanetty.kafka.consumer.KafkaConsumer;
import com.kafka.kafkanetty.kafka.consumer.controller.MessageController;

public class KafKaConsumerTest {

	KafkaConsumer consumer = new KafkaConsumer();
	MessageController controller = new MessageController();
	
	@Test
	public void test() {
		
		
		consumer
			.poll(100)
			.mapToObj(num-> new ClientRequestWrapper<Integer>(num))
			.forEach(req -> controller.route(req));
		
		
	}


}
