package com.kafka.kafkanetty.client.test.manager;

import static org.mockito.Mockito.times;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

import util.MsgFromKafkaAndroid;
import util.MsgFromKafkaSmss;
import util.TestUtil;

@DisplayName("MSG 타입(SMS, PUSH), 종류(대량, 단건)에 따라 목적으로 하는 객체가 Invoked 하는지")
public class DynamicHandlerMngTest {
	
	FirebaseMessaging instance = TestUtil.instance;
	SmsPushMapper mapper = TestUtil.mapper;

	SendManager smsSingle = TestUtil.smsSingle;
	SendManager smsMulti = TestUtil.smsMulti;
	SendManager pushSingle = TestUtil.pushSingle;
	SendManager pushMulti = TestUtil.pushMulti;
	TempMongodbTemplate mongo = TestUtil.mongo;
	

	DynamicHandlerManager manager =  new DynamicHandlerManager(smsSingle, smsMulti, pushSingle, pushMulti,mongo);
	
	MsgFromKafkaVo voForMultipleSMS = MsgFromKafkaSmss.voForMultipleSMSWithValidDataBody;
	
	MsgFromKafkaVo voForSingleSMS = MsgFromKafkaSmss.voForSingleSMSWithInValidDataBodyIwthInValidHeader;
	
	MsgFromKafkaVo voForMultipleAndroidPush =  MsgFromKafkaAndroid.voForMultiplePushWithValidDataBody;
	
	MsgFromKafkaVo voForSingleAndroidPush =  MsgFromKafkaAndroid.voForSinglePushWithValidDataBody;
	
	
	
	UserInfoOnPush userInfoOnPush = TestUtil.userInfoOnPushWithYes;
	UserInfoOnPush userInfoOnPushWithNo = TestUtil.userInfoOnPushWithNo;

	@AfterEach
	public void cleanUp() {
		Mockito.reset(mapper);

		Mockito.reset(mongo);
	}
	
	@Test
	@DisplayName("SMS 대량 발송 테스트")
	public void test() {
	
		checkIfSendInvokedAccordingToTheTypeOfVo(manager, voForMultipleSMS, smsMulti);
		
	
	}


	
	@Test
	@DisplayName("SMS 단건 발송 테스트")
	public void test1() {
	
		
		checkIfSendInvokedAccordingToTheTypeOfVo(manager, voForSingleSMS, smsSingle);		
	
	}
	
	@Test
	@DisplayName("Android PUSH 대량 발송 테스트")
	public void test2() {
	
		checkIfSendInvokedAccordingToTheTypeOfVo(manager, voForMultipleAndroidPush,pushMulti);		


	}

	@Test
	@DisplayName("Android PUSH 단건 발송 테스트")
	public void test3() {
		
		seeIfSendManagerInVokedWhenPassedValidVo(manager, voForSingleAndroidPush,pushSingle,mapper,userInfoOnPush);

	}

	
	
	@Test
	@DisplayName("요청받은 USER가 Notification을 수신하지 않는 경우 UserNotAllowNotificationException 에러와 함께 해당 메시지 이력을 몽고DB에 저장한다.")
	public void test4() {
		
		
		seeIfUserNotAllowToGetNotified(manager, voForSingleAndroidPush, pushSingle, mapper, mongo, userInfoOnPushWithNo);
	}

	
	
	@Test
	@DisplayName("DB에서 불러온 유저정보가 Invalid 한 경우, UserInfoInvalidException 에러와 함께 해당 메시지 이력을 몽고DB에 저장한다.")
	public void test5() {
		
		
		
		UserInfoOnPush mock =  Mockito.mock(UserInfoOnPush.class);
		
		Mockito.when(mock.validation()).thenReturn(false);

		seeIfUserNotAllowToGetNotified(manager, voForSingleAndroidPush, pushSingle, mapper, mongo,mock);



	}
	
	

		
	private void seeIfUserNotAllowToGetNotified(DynamicHandlerManager manager, MsgFromKafkaVo vo, SendManager sendMng, SmsPushMapper mapper, TempMongodbTemplate mongo, UserInfoOnPush info) {
		Mockito.when(mapper.getIfSendYnByUserNo(vo.getTarget().get(0))).thenReturn(info);
		
		ResultOfPush result =  manager.consume(vo);

		Mockito.verify(mongo, times(1)).insertDbHistory(result);
	}
	
		
	private void checkIfSendInvokedAccordingToTheTypeOfVo(DynamicHandlerManager manager, MsgFromKafkaVo vo, SendManager sendMng) {
		manager.consume(vo);
		Mockito.verify(sendMng, times(1)).send(vo);
	}
	
	private void seeIfSendManagerInVokedWhenPassedValidVo(DynamicHandlerManager manager, MsgFromKafkaVo vo, SendManager sendMng, SmsPushMapper mapper, UserInfoOnPush push) {
		Mockito.when(mapper.getIfSendYnByUserNo(vo.getTarget().get(0))).thenReturn(push);
	
		checkIfSendInvokedAccordingToTheTypeOfVo(manager,vo,sendMng);
	}
	
}
