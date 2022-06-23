package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.kafka.consumer.controller.TestVos;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kt.onnuipay.kafka.kafkanetty.exception.RunTimeExceptionWrapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

import datavo.msg.MessageWrapper;
import util.TestUtil;

/**
 * 1. MSG 타입 및 종류(안드로이드? IOS? SMS? / 단건 발송? 멀티발송?)에 따라, 해당하는 핸들러로 메시지를 라우팅한다.
 * 2. 하위 개체들이 핸들링하지 못한 에러가 발생한 경우, 에러를 RuntimeException으로 Wrapping한 이후, dispatchController에서 처리하도록 한다.
 * 3. 결과를 DispatchController로 반환한다.
 * 
 * 
 * **/

@DisplayName("MSG 타입(SMS, PUSH), 종류(대량, 단건)에 따라 목적으로 하는 객체가 Invoked 하는지")
public class DynamicHandlerMngTest {
	
	FirebaseMessaging mockFirebaseInstance = TestUtil.instance;
	SmsPushMapper mockForMapper = TestUtil.mapper;
	
	SendManager smsSingle = Mockito.mock(TestUtil.smsSingle.getClass());
	SendManager smsMulti = Mockito.mock(TestUtil.smsMulti.getClass());
	SendManager pushSingle = Mockito.mock(TestUtil.pushSingle.getClass());
	SendManager pushMulti = Mockito.mock(TestUtil.pushMulti.getClass());
	TempMongodbTemplate mongo = Mockito.mock(TestUtil.mockingMongo.getClass());
	
	
	DynamicHandlerManager manager;
	
	TestVos vos = TestVos.getTestVos();	

	ResultOfPush successResult = TestUtil.createSuccessResultOfPushGivenVo(vos.getVoForSingleAndroidPush(),true);
	ResultOfPush failResult = TestUtil.createFailResultOfPushGivenVo(vos.getVoForSingleAndroidPush(),true, new RuntimeException());


	@BeforeEach
	public void cleanUp() {

		
		TestUtil.resetMockingObj(
				this.mockForMapper, 
				this.mockFirebaseInstance, 
				this.mongo, 
				this.smsSingle,
				this.smsMulti,
				this.pushSingle,
				this.pushMulti
				);

		this.manager =  new DynamicHandlerManager(smsSingle, smsMulti, pushSingle, pushMulti);
	}
	
	@Test
	@DisplayName("SMS 대량 발송 테스트")
	public void test() {
		checkIfSendInvokedAccordingToTheTypeOfVo(manager, vos.getVoForMultipleSMS(), smsMulti);
	}

	@Test
	@DisplayName("SMS 단건 발송 테스트")
	public void test1() {
	
		checkIfSendInvokedAccordingToTheTypeOfVo(manager, vos.getVoForSingleSMS(), smsSingle);		

	}
	
	@Test
	@DisplayName("Android PUSH 대량 발송 테스트")
	public void test2() {
	
		checkIfSendInvokedAccordingToTheTypeOfVo(manager,vos.getVoForMultipleAndroidPush(),pushMulti);		


	}

	@Test
	@DisplayName("Android PUSH 단건 발송 테스트")
	public void test3() {
		
		checkIfSendInvokedAccordingToTheTypeOfVo(manager, vos.getVoForSingleAndroidPush(),pushSingle);

	}
	
	@Test
	@DisplayName("dynamicmanager는 senderManager이 반환한 result를 그대로 반환한다.")
	public void test4() {
		
	
		
	}
	
	@Test
	@DisplayName("dynamicmanager는 senderManager에서 에러가 발생할 시, 에러 정보를 담은 fail Result를 반환한다.")
	public void test4_1() {
		
	
	}
	
	@Test
	@DisplayName("dynamicmanager는 senderManager에서 에러가 발생했는데, 해당 객체에서 핸들링하지 못한 경우, 에러를 RunTimeExceptionWrapper로 감싸서 위로 던진다")
	public void test4_2() {
		
		
	}
	


		
	private void checkIfSendInvokedAccordingToTheTypeOfVo(DynamicHandlerManager manager, MessageWrapper messageWrapper, SendManager sendMng) {
		manager.consume(messageWrapper);
		Mockito.verify(sendMng, times(1)).send(messageWrapper);
	}
	
	
}
