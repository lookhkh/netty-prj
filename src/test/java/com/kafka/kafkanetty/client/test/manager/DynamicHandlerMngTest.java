package com.kafka.kafkanetty.client.test.manager;

import static org.mockito.Mockito.atLeast;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

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
	
	MsgFromKafkaVo voForMultipleSMS = TestUtil.voForMultipleSMS;
	
	MsgFromKafkaVo voForSingleSMS = TestUtil.voForSingleSMS;
	
	MsgFromKafkaVo voForMultiplePush =  TestUtil.voForMultiplePush;
	
	MsgFromKafkaVo voForSinglePush =  TestUtil.voForSinglePush;
	
	UserInfoOnPush userInfoOnPush = TestUtil.userInfoOnPushWithYes;
	UserInfoOnPush userInfoOnPushWithNo = TestUtil.userInfoOnPushWithNo;

	
	@Test
	@DisplayName("SMS 대량 발송 테스트")
	public void test() {
	
		manager.consume(voForMultipleSMS);
		Mockito.verify(smsMulti, atLeast(1)).send(voForMultipleSMS);
		
	
	}
	
	@Test
	@DisplayName("SMS 단건 발송 테스트")
	public void test1() {
	
		manager.consume(voForSingleSMS);
		Mockito.verify(smsSingle, atLeast(1)).send(voForSingleSMS);

		
	
	}
	
	@Test
	@DisplayName("PUSH 대량 발송 테스트")
	public void test2() {
	
		manager.consume(voForMultiplePush);
		Mockito.verify(pushMulti, atLeast(1)).send(voForMultiplePush);

	
	}
	
	@Test
	@DisplayName("PUSH 단건 발송 테스트")
	public void test3() {
		
		Mockito.when(mapper.getIfSendYnByUserNo(voForSinglePush)).thenReturn(userInfoOnPush);
	
		manager.consume(voForSinglePush);
		Mockito.verify(pushSingle, atLeast(1)).send(voForSinglePush);

	}
	
	@Test
	@DisplayName("요청받은 USER가 Notification을 수신하지 않는 경우 UserNotAllowNotificationException 에러와 함께 해당 메시지 이력을 몽고DB에 저장한다.")
	public void test4() {
		Mockito.when(mapper.getIfSendYnByUserNo(voForSinglePush)).thenReturn(userInfoOnPushWithNo);
		ResultOfPush result =  manager.consume(voForSinglePush);

		Mockito.verify(mongo, atLeast(1)).insertDbHistory(result);
	}
	
	
	@Test
	@DisplayName("DB에서 불러온 유저정보가 Invalid 한 경우, UserInfoInvalidException 에러와 함께 해당 메시지 이력을 몽고DB에 저장한다.")
	public void test5() {
		UserInfoOnPush mock =  Mockito.mock(UserInfoOnPush.class);

		Mockito.when(mapper.getIfSendYnByUserNo(voForSinglePush)).thenReturn(mock);
		Mockito.when(mock.validation()).thenReturn(false);
		
		ResultOfPush result = manager.consume(voForSinglePush);
		
		Mockito.verify(mongo, atLeast(1)).insertDbHistory(result);


	}
}
