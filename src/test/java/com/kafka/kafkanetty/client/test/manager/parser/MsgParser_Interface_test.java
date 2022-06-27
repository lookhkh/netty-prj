package com.kafka.kafkanetty.client.test.manager.parser;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

@DisplayName("Message를 MessageWrapper로 파싱한다.")
public class MsgParser_Interface_test {

	KafkaMsgParser parser = mock(KafkaMsgParser.class);
	
	@DisplayName("invalid한 메시지가 들어올 경우, JsonDataParsing 에러를 던진다.")
	@Test
	public void test1() {
		
		
	}
}
