package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParserImpl;

import datavo.MetaData;
import datavo.msg.MessageWrapper;
import datavo.msg.util.MessageUtils;

@DisplayName("메세지 파서 테스트")
public class MsgParserTest {

	
	KafkaMsgParser parser = new KafkaMsgParserImpl();

	MessageWrapper voMultiPush = datavo.testUtils.MsgFromKafkaIOS.voForMultipleIOSPush;
	MessageWrapper voSinglePush = datavo.testUtils.MsgFromKafkaIOS.voForSingleIOSPush;
	
	String jsonForMulti;
	String jsonForSingle;
	
	@BeforeEach
	public void init() {
		
		this.jsonForMulti = MessageUtils.toJson(voMultiPush, MessageWrapper.class);
		this.jsonForSingle = MessageUtils.toJson(voSinglePush, MessageWrapper.class);
		
		
	}
	
	
	@Test
	@DisplayName("parser에 잘못된 형식의 데이터를 집어넣을 경우 JsonDataProcessingWrapperException이 터진다.")
	public void test52() {
		assertThrows(JsonDataProcessingWrapperException.class, () -> parser.parse("hi"));
	}
	
	@Test
	@DisplayName("parse에 JSON - Multi 값이 입력되면, Metadata 파싱 후, 타입을 결정한다.")
	public void test1() throws IOException {
		
		MessageWrapper result =  parser.parse(jsonForMulti);
		
		assertNotNull(result);
		
		
		MetaData data =  result.getMetaData();
		MulticastMessage msg = (MulticastMessage) result.getMessage();
		
		assertNotNull(data);
		assertNotNull(msg);
		
		assertTrue(result.getCodeOfType()== 1);
		assertTrue(msg instanceof MulticastMessage);
	}	
	
	@Test
	@DisplayName("parse에 JSON - Signle 값이 입력되면, Metadata 파싱 후, 타입을 결정한다.")
	public void test1_1() throws IOException {
		
		MessageWrapper result =  parser.parse(jsonForSingle);
		
		assertNotNull(result);
		
		
		MetaData data =  result.getMetaData();
		Message msg = (Message) result.getMessage();
		
		assertNotNull(data);
		assertNotNull(msg);
		
		assertTrue(result.getCodeOfType()== 0);
		assertTrue(msg instanceof Message);
	}	
	

	
	
	
}
