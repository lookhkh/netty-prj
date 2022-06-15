package com.kafka.kafkanetty.kafka.consumer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.onnuipay.kafka.kafkanetty.exception.InvalidMsgFormatException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherController;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherControllerImpl;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

import util.MsgFromKafkaAndroid;
import util.MsgFromKafkaIOS;
import util.MsgFromKafkaSmss;
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
	
	
	
	MsgFromKafkaVo voForSingleAndroidPush =  MsgFromKafkaAndroid.voForSinglePushWithValidDataBody;
	MsgFromKafkaVo voForMultipleAndroidPush =  MsgFromKafkaAndroid.voForMultiplePushWithValidDataBody;

	
	MsgFromKafkaVo voForSingleIOSPush =  MsgFromKafkaIOS.voForSinglePushWithValidDataBody;
	MsgFromKafkaVo voForMultipleIOSPush =  MsgFromKafkaIOS.voForMultiplePushWithValidDataBody;

	
	MsgFromKafkaVo voForMultipleSMS =  MsgFromKafkaSmss.voForMultipleSMSWithValidDataBody;



	
	String jsonForSingleAndroidPush;
	String jsonForSingleIOSPush;
	String jsonForSingleSMS;
	

	
	@BeforeEach
	public void init() throws JsonProcessingException {
		jsonForSingleAndroidPush = mapper.writeValueAsString(voForSingleAndroidPush);
		jsonForSingleIOSPush = mapper.writeValueAsString(voForSingleIOSPush);
		jsonForSingleSMS = mapper.writeValueAsString(voForMultipleSMS);

	}
	
	@Test
	@DisplayName("메시지 전송 타입 테스트 : 0=> 단건 전송 | 1=> 대량 발송 / 메시지 Key => 안드로이드 0 | IOS 1 | SMS 2 ")
	public void test() throws JsonProcessingException {
		assertEquals(voForSingleAndroidPush.getCodeOfType(), 0); 
		assertEquals(voForMultipleSMS.getCodeOfType(), 1); 
		
		assertEquals(voForSingleAndroidPush.getTypeValue(), 0); 
		assertEquals(voForMultipleSMS.getTypeValue(), 2); 
		assertEquals(voForSingleIOSPush.getTypeValue(), 1); 

	}
	
	@Test
	@DisplayName("ANDROID 단건 MSG 수신 후 push 후 성공 반환")
	public void test1() throws DatabindException, InvalidMsgFormatException, IOException {
		
		Mockito.when(parser.parse(jsonForSingleAndroidPush)).thenReturn(voForSingleAndroidPush);
		Mockito.when(m.consume(voForSingleAndroidPush)).thenReturn(createResultObj(voForSingleAndroidPush));
		
		
		ResultOfPush p =  controller.route(jsonForSingleAndroidPush);
		
		assertTrue(p.isSuccess());
	}
	


	@Test
	@DisplayName("수신한 메시지의 포맷이 부적절한 경우 실패를 반환")
	public void test2() throws DatabindException, InvalidMsgFormatException, IOException {
		
		Mockito.when(parser.parse(ArgumentMatchers.anyString())).thenThrow(InvalidMsgFormatException.class);
		
		
		ResultOfPush p =  controller.route(ArgumentMatchers.anyString());
		
		assertFalse(p.isSuccess());
	}
	
	
	
	private ResultOfPush createResultObj(MsgFromKafkaVo vo) {
		return 	
				ResultOfPush.builder()
				.id("test")
				.success(true)
				.vo(vo)
				.build();
	}
	
	
}
