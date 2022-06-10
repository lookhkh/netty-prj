package com.kafka.kafkanetty.kafka.consumer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.kafkanetty.exception.InvalidMsgFormatException;
import com.kafka.kafkanetty.kafka.DispatcherController;
import com.kafka.kafkanetty.kafka.DispatcherControllerImpl;
import com.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kafka.kafkanetty.kafka.model.enums.TypeOfSending;
import com.kafka.kafkanetty.kafka.parser.KafkaMsgParser;
import com.kafka.kafkanetty.kafka.parser.KafkaMsgParserImpl;

import util.TestUtil;

/*
 * 
 * Kafka로부터 MSG를 POLL한 이후, 
 * DynamicHandlerManager으로 전달한 이후 결과를 바탕으로 kafka에 commit 한다.
 * 
 * */
@DisplayName("메시지 수신 후 파싱 및 알맞은 객체 호출")
public class ControllerTest {

	DynamicHandlerManager m = TestUtil.m;
	KafkaMsgParser parser = TestUtil.parser;
	
	DispatcherController controller = new DispatcherControllerImpl(parser,m);
	ObjectMapper mapper = new ObjectMapper();
	
	
	
	MsgFromKafkaVo voForAndroid =  TestUtil.voForAndroid;
	
	MsgFromKafkaVo voForIOS =  TestUtil.voForIOS;

	
	MsgFromKafkaVo voForSMS =  TestUtil.voForSMS;
	
	ResultOfPush result = ResultOfPush.builder()
							.id("test")
							.success(true)
							.vo(voForAndroid)
							.build();

	
	
	String jsonForAndroid;
	String jsonForIOS;
	String jsonForSMS;
	
	
	
	@BeforeEach
	public void init() throws JsonProcessingException {
		jsonForAndroid = mapper.writeValueAsString(voForAndroid);
		jsonForIOS = mapper.writeValueAsString(voForIOS);
		jsonForSMS = mapper.writeValueAsString(voForSMS);

	}
	
	@Test
	@DisplayName("메시지 전송 타입 테스트 : 0=> 단건 전송 | 1=> 대량 발송")
	public void test() throws JsonProcessingException {
		assertEquals(voForAndroid.getCodeOfType(), 0); 
		assertEquals(voForSMS.getCodeOfType(), 1); 
	}
	
	@Test
	@DisplayName("ANDROID 단건 MSG 수신 후 push 후 성공 반환")
	public void test1() {
		
		Mockito.when(parser.parse(jsonForAndroid)).thenReturn(voForAndroid);
		Mockito.when(m.consume(voForAndroid)).thenReturn(result);
		
		
		ResultOfPush p =  controller.route(jsonForAndroid);
		
		assertTrue(p.isSuccess());
	}
	
	@Test
	@DisplayName("수신한 메시지의 포맷이 부적절한 경우 실패를 반환")
	public void test2() {
		
		Mockito.when(parser.parse(jsonForAndroid)).thenThrow(InvalidMsgFormatException.class);
		
		
		ResultOfPush p =  controller.route(jsonForAndroid);
		
		assertFalse(p.isSuccess());
	}
	
	
	
	
	
	
}
