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
import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherController;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherControllerImpl;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

import datavo.msg.MultiMessageWrapper;
import datavo.msg.SingleMessageWrapper;
import util.MsgFromKafkaAndroid;
import util.MsgFromKafkaIOS;
import util.MsgFromKafkaSmss;
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

	DynamicHandlerManager mockingDynamicHanlder = TestUtil.mockingDynamicHanlder;
	KafkaMsgParser mockingParser = TestUtil.mockingParser;
	TempMongodbTemplate mockingMongo = TestUtil.mongo;
		
	DispatcherController controller;
	ObjectMapper mapper = new ObjectMapper();
	
	
	
	SingleMessageWrapper voForSingleAndroidPush =  MsgFromKafkaAndroid.voForSinglePushWithValidDataBody;
	MultiMessageWrapper voForMultipleAndroidPush =  MsgFromKafkaAndroid.voForMultiplePush;

	
	SingleMessageWrapper voForSingleIOSPush =  MsgFromKafkaIOS.voForSingleIOSPush;
	MultiMessageWrapper voForMultipleIOSPush =  MsgFromKafkaIOS.voForMultipleIOSPush;

	
	SingleMessageWrapper voForMultipleSMS =  MsgFromKafkaSmss.voForSingleSmsWithSMS;
	MultiMessageWrapper voForSingleSMS =  MsgFromKafkaSmss.voForMultipleSMSWithSMS;

	SingleMessageWrapper voForMultipleLMS =  MsgFromKafkaSmss.voForSingleSmsWithLMS;
	MultiMessageWrapper voForSingleLMS =  MsgFromKafkaSmss.voForMultipleSMSWithLMS;


	String jsonForSingleAndroidPush;
	String jsonForSingleIOSPush;
	String jsonForSingleSMS;
	String jsonForMultiAndroidPush;
	String jsonForMultiIOSPush;
	String jsonForMultiSMS;
	

	String a = "1";
	
	@BeforeEach
	public void init() throws JsonProcessingException {
		jsonForSingleAndroidPush = mapper.writeValueAsString(voForSingleAndroidPush);
		jsonForSingleIOSPush = mapper.writeValueAsString(voForSingleIOSPush);
		jsonForSingleSMS = mapper.writeValueAsString(voForMultipleSMS);
		jsonForMultiAndroidPush = mapper.writeValueAsString(voForMultipleAndroidPush);
		jsonForMultiIOSPush = mapper.writeValueAsString(voForMultipleIOSPush);
		jsonForMultiSMS = mapper.writeValueAsString(voForMultipleSMS);
		
		this.mockingDynamicHanlder = Mockito.mock(DynamicHandlerManager.class);
		this.mockingParser = Mockito.mock(KafkaMsgParser.class);
		this.mockingMongo = Mockito.mock(TempMongodbTemplate.class);
		controller = new DispatcherControllerImpl(mockingParser,mockingDynamicHanlder,mockingMongo);

	}

	
	
	@Test
	@DisplayName("ANDROID 단건 MSG 수신 후 push 후 성공 반환하며, 이력을 DB에 저장한다.")
	public void test1() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForSingleAndroidPush,voForSingleAndroidPush,true);
	}

	
	@Test
	@DisplayName("ANDROID Multi MSG 수신 후 push 후 성공 반환, 이력을 DB에 저장한다.")
	public void test1_1() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForMultiAndroidPush,voForMultipleAndroidPush,true);

	}
	
	
	@Test
	@DisplayName("IOS 단건 MSG 수신 후 push 후 성공 반환, 이력을 DB에 저장한다.")
	public void test1_2() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForSingleIOSPush,voForSingleIOSPush,true);

	}
	
	@Test
	@DisplayName("IOS Multi MSG 수신 후 push 후 성공 반환, 이력을 DB에 저장한다.")
	public void test1_3() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForMultiIOSPush,voForMultipleIOSPush,true);

	}
	
	@Test
	@DisplayName("SMS 단건 MSG 수신 후 push 후 성공 반환, 이력을 DB에 저장한다.")
	public void test1_4() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForSingleSMS,voForSingleSMS,true);

	}
	
	@Test
	@DisplayName("SMS Multi MSG 수신 후 push 후 성공 반환, 이력을 DB에 저장한다.")
	public void test1_5() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForMultiSMS,voForMultipleSMS,true);

		
	}
	
	
	///////////////////////////////////////////
	
	
	@Test
	@DisplayName("ANDROID 단건 MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_6() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForSingleAndroidPush,voForSingleAndroidPush,false);
	}

	
	@Test
	@DisplayName("ANDROID Multi MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_7() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForMultiAndroidPush,voForMultipleAndroidPush,false);

	}
	
	
	@Test
	@DisplayName("IOS 단건 MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_8() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForSingleIOSPush,voForSingleIOSPush,false);

	}
	
	@Test
	@DisplayName("IOS Multi MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_9() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForMultiIOSPush,voForMultipleIOSPush,false);

	}
	
	@Test
	@DisplayName("SMS 단건 MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_10() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForSingleSMS,voForSingleSMS,false);

	}
	
	@Test
	@DisplayName("SMS Multi MSG 수신 후 push 후 실패 반환, 이력을 DB에 저장한다.")
	public void test1_11() throws DatabindException, IOException {
		
		controllerTestCondition(jsonForMultiSMS,voForMultipleSMS,false);

		
	}
	
	//////////////////////////////////////////
	
	
	



	@Test
	@DisplayName("수신한 메시지의 포맷이 부적절한 경우 실패를 반환")
	public void test2() throws DatabindException, IOException {
		
		Mockito.when(mockingParser.parse(ArgumentMatchers.anyString())).thenThrow(JsonDataProcessingWrapperException.class);
		
		ResultOfPush p =  controller.route(ArgumentMatchers.anyString());
		
		assertFalse(p.isSuccess());
		Mockito.verify(mockingMongo, Mockito.only()).insertDbHistory(p);
		assertNull(p.getVo());

	}
	
	@Test
	@DisplayName("내부에서 처리못한 Exception이 터진 경우, 실패를 반환하며, DB에 이력을 저장한다.")
	public void test3() {
		
		RunTimeExceptionWrapper t = new RunTimeExceptionWrapper("", voForMultipleIOSPush, new Exception(""));
		
		Mockito.when(mockingParser.parse(jsonForMultiIOSPush)).thenReturn(voForMultipleIOSPush);
		Mockito.when(mockingDynamicHanlder.consume(voForMultipleIOSPush)).thenThrow(t);

		ResultOfPush p =  controller.route(jsonForMultiIOSPush);
		
		assertNotNull(p);
		
		assertFalse(p.isSuccess());
		Mockito.verify(mockingMongo, Mockito.only()).insertDbHistory(p);
		assertNotNull(p.getVo());
		
		assertEquals(t.getVo(), p.getVo());
	}
	
	
	
	private void controllerTestCondition(String json, MsgFromKafkaVo resultVo, boolean result) {
		Mockito.when(mockingParser.parse(json)).thenReturn(resultVo);
		Mockito.when(mockingDynamicHanlder.consume(resultVo)).thenReturn(createResultObj(resultVo,result));
		
		
		ResultOfPush p =  controller.route(json);
		
		if(result) {
			assertTrue(p.isSuccess());

		}else {
			assertFalse(p.isSuccess());

		}
		Mockito.verify(mockingMongo, Mockito.only()).insertDbHistory(p);
		assertEquals(p.getVo(), resultVo);
	}
	
	private ResultOfPush createResultObj(MsgFromKafkaVo vo, boolean result) {
		return 	
				ResultOfPush.builder()
				.id("test")
				.success(result)
				.vo(vo)
				.build();
	}
	
	

	
	
}
