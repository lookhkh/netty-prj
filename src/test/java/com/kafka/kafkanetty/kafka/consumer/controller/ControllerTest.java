package com.kafka.kafkanetty.kafka.consumer.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.kafkanetty.kafka.DispatcherController;
import com.kafka.kafkanetty.kafka.model.KafkaKeyEnum;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

/*
 * 
 * Kafka로부터 MSG를 POLL한 이후, 
 * 메시지 타입에 맞게 알맞은 서비스(ANDROID,IOS,Xroshot)으로 라우팅한다.
 * 
 * */
public class ControllerTest {

	DispatcherController controller = new DispatcherController(new KafkaMsgParser());
	ObjectMapper mapper = new ObjectMapper();
	MsgFromKafkaVo voForAndroid =  MsgFromKafkaVo.builder()
							.key(KafkaKeyEnum.ANDROID)
							.payload("TEST For Android")
							.build();
	
	MsgFromKafkaVo voForIOS =  MsgFromKafkaVo.builder()
			.key(KafkaKeyEnum.IOS)
			.payload("TEST For IOS")
			.build();
	
	MsgFromKafkaVo voForSMS =  MsgFromKafkaVo.builder()
			.key(KafkaKeyEnum.SMS)
			.payload("TEST For sms")
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
	public void test() throws JsonProcessingException {
		
        
		controller.route(jsonForAndroid);
		controller.route(jsonForIOS);
		controller.route(jsonForSMS);

	}
	
	
}
