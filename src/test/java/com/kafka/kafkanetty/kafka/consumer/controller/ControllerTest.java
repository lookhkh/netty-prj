package com.kafka.kafkanetty.kafka.consumer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.kafkanetty.kafka.DispatcherController;
import com.kafka.kafkanetty.kafka.DispatcherControllerImpl;
import com.kafka.kafkanetty.kafka.model.KafkaKeyEnum;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.TypeOfSending;
import com.kafka.kafkanetty.kafka.parser.KafkaMsgParserImpl;

import util.TestUtil;

/*
 * 
 * Kafka로부터 MSG를 POLL한 이후, 
 * 메시지 타입에 맞게 알맞은 서비스(ANDROID,IOS,Xroshot)으로 라우팅한다.
 * 
 * */
@DisplayName("메시지 수신 후 파싱 및 알맞은 객체 호출")
public class ControllerTest {

	DispatcherController controller = new DispatcherControllerImpl(new KafkaMsgParserImpl());
	ObjectMapper mapper = new ObjectMapper();
	
	
	
	
	MsgFromKafkaVo voForAndroid =  TestUtil.voForAndroid;
	
	MsgFromKafkaVo voForIOS =  TestUtil.voForIOS;

	
	MsgFromKafkaVo voForSMS =  TestUtil.voForSMS;
	
	
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
	
	
	
}
