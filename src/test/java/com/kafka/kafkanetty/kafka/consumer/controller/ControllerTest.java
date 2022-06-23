package com.kafka.kafkanetty.kafka.consumer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;
import com.kt.onnuipay.kafka.kafkanetty.exception.RunTimeExceptionWrapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherControllerImpl;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

import datavo.msg.MessageWrapper;
import datavo.msg.util.MessageUtils;
import datavo.testUtils.MsgFromKafkaAndroid;
import datavo.testUtils.MsgFromKafkaIOS;
import datavo.testUtils.MsgFromKafkaSmss;
import util.TestUtil;

/*
 * 
 * 1. Kafka로부터 MSG를 POLL한 이후,
 * 2. 메시지를 파싱한다. 메시지 파싱 시, 실패할 경우, 예외를 반환하고, 결과를 저장한다. 
 * DynamicHandlerManager으로 전달한 이후 결과를 바탕으로 kafka에 commit 한다.
 * 
 * */
@DisplayName("메시지 수신 후 파싱 및 알맞은 객체 호출")
public class ControllerTest {

	TestVos data = new TestVos(TestUtil.mockingDynamicHanlder, TestUtil.mockingParser, TestUtil.mongo, new ObjectMapper(), MsgFromKafkaAndroid.voForSinglePushWithValidDataBody,
			MsgFromKafkaAndroid.voForMultiplePush, MsgFromKafkaIOS.voForSingleIOSPush, MsgFromKafkaIOS.voForMultipleIOSPush, MsgFromKafkaSmss.voForSingleSmsWithSMS, MsgFromKafkaSmss.voForMultipleSMSWithSMS, MsgFromKafkaSmss.voForSingleSmsWithLMS,
			MsgFromKafkaSmss.voForMultipleSMSWithLMS);

	

	@BeforeEach
	public void init() throws JsonProcessingException {
		data.jsonForSingleAndroidPush = MessageUtils.toJson(data.voForSingleAndroidPush, MessageWrapper.class);
		data.jsonForSingleIOSPush = MessageUtils.toJson(data.voForSingleIOSPush, MessageWrapper.class);
		data.jsonForSingleSMS = MessageUtils.toJson(data.voForMultipleSMS, MessageWrapper.class);
		data.jsonForMultiAndroidPush = MessageUtils.toJson(data.voForMultipleAndroidPush, MessageWrapper.class);
		data.jsonForMultiIOSPush = MessageUtils.toJson(data.voForMultipleIOSPush, MessageWrapper.class);
		data.jsonForMultiSMS = MessageUtils.toJson(data.voForMultipleSMS, MessageWrapper.class);
		
		this.data.mockingDynamicHanlder = Mockito.mock(DynamicHandlerManager.class);
		this.data.mockingParser = Mockito.mock(KafkaMsgParser.class);
		this.data.mockingMongo = Mockito.mock(TempMongodbTemplate.class);
		data.controller = new DispatcherControllerImpl(data.mockingParser,data.mockingDynamicHanlder,data.mockingMongo);

	}

	
	
	@Test
	@DisplayName("ANDROID 단건 MSG 수신 후 push 후 성공 반환하며, 이력을 DB에 저장한다.")
	public void test1() throws DatabindException, IOException {
		
	}

	
	@Test
	@DisplayName("ANDROID Multi MSG 수신 후 push 후 성공 반환, 이력을 DB에 저장한다.")
	public void test1_1() throws DatabindException, IOException {
		

	}
	
	
	@Test
	@DisplayName("IOS 단건 MSG 수신 후 push 후 성공 반환, 이력을 DB에 저장한다.")
	public void test1_2() throws DatabindException, IOException {
		

	}
	
	@Test
	@DisplayName("IOS Multi MSG 수신 후 push 후 성공 반환, 이력을 DB에 저장한다.")
	public void test1_3() throws DatabindException, IOException {
		

	}
	
	@Test
	@DisplayName("SMS 단건 MSG 수신 후 push 후 성공 반환, 이력을 DB에 저장한다.")
	public void test1_4() throws DatabindException, IOException {
		

	}
	
	@Test
	@DisplayName("SMS Multi MSG 수신 후 push 후 성공 반환, 이력을 DB에 저장한다.")
	public void test1_5() throws DatabindException, IOException {
		

		
	}
	
	
	///////////////////////////////////////////
	
	
	@Test
	@DisplayName("ANDROID 단건 MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_6() throws DatabindException, IOException {
		
	}

	
	@Test
	@DisplayName("ANDROID Multi MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_7() throws DatabindException, IOException {
		

	}
	
	
	@Test
	@DisplayName("IOS 단건 MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_8() throws DatabindException, IOException {
		

	}
	
	@Test
	@DisplayName("IOS Multi MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_9() throws DatabindException, IOException {
		

	}
	
	@Test
	@DisplayName("SMS 단건 MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_10() throws DatabindException, IOException {
		

	}
	
	@Test
	@DisplayName("SMS Multi MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_11() throws DatabindException, IOException {
		

		
	}
	
	//////////////////////////////////////////
	
	
	



	@Test
	@DisplayName("수신한 메시지의 포맷이 부적절한 경우 실패를 반환")
	public void test2() throws DatabindException, IOException {
		
		Mockito.when(data.mockingParser.parse(ArgumentMatchers.anyString())).thenThrow(JsonDataProcessingWrapperException.class);
		
		data.controller.route(ArgumentMatchers.anyString());
		
		
	}
	
	@Test
	@DisplayName("내부에서 처리못한 Exception이 터진 경우, 실패를 반환하며, DB에 이력을 저장한다.")
	public void test3() {
		
	
	}
	
	
	
	
	
	private ResultOfPush createResultObj(MessageWrapper voForSingleAndroidPush2, boolean result) {
		return 	
				ResultOfPush.builder()
				.metaData(voForSingleAndroidPush2.getMetaData())
				.isSuccess(result)
				.vo(voForSingleAndroidPush2)
				.build();
	}
	
	

	
	
}
