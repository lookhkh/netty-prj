package com.kafka.kafkanetty.kafka.consumer.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherControllerImpl;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.listener.AckMessageListener;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

import util.TestUtil;

@DisplayName("통합 테스트 용, 현재는 parser 부분이 미개발 중이라 계속 에러 나올 것임 220609 조현일")
public class ListenerTest {

	DynamicHandlerManager m = TestUtil.m;
	KafkaMsgParser parser = TestUtil.parser;
	AckMessageListener listener = new AckMessageListener(new DispatcherControllerImpl(parser, m));
	
	
	@Test
	@DisplayName("통합 테스트 용, 현재는 parser 부분이 미개발 중이라 계속 에러 나올 것임 220609 조현일")
	public void test() {
		listener.listen("hi");
	}
	
}
