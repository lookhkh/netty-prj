package com.kafka.kafkanetty.kafka.consumer.controller;

import org.junit.jupiter.api.Test;

import com.kafka.kafkanetty.kafka.DispatcherController;
import com.kafka.kafkanetty.kafka.listener.AckMessageListener;
import com.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

public class ListenerTest {

	
	AckMessageListener listener = new AckMessageListener(new DispatcherController(new KafkaMsgParser()));
	
	@Test
	public void test() {
		listener.listen("hi");
	}
	
}
