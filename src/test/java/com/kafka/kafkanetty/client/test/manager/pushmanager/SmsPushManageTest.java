package com.kafka.kafkanetty.client.test.manager.pushmanager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.client.handler.manager.SendPushManager;
import com.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kafka.kafkanetty.kafka.model.push.IOSVo;

import util.TestUtil;

/**
 * @implNote com.kafka.kafkanetty.client.handler.manager.SendPushManager 테스트 케이스 모음 <br>
 * 1. Vo 객체를 실제 FCM로 보내기 위하여 ANDROID, IOS에 따른 객체 Parsing <br>
 * 2. 변환된 객체를 FCM으로 전송 및 결과 확인 <br>
 * 3. 예외 상황 처리 및 결과 return <br>
 * 
 * **/
@DisplayName("SMS PUSH manager 테스트, 직접적 외부연동 객체를 다룸")
public class SmsPushManageTest {


	SendPushManager manager = Mockito.mock(SendPushManager.class);
	
	FirebaseMessaging instance = TestUtil.instance;
	SmsPushMapper mapper = TestUtil.mapper;

	SendManager mng = TestUtil.pushSingle;
	
	MsgFromKafkaVo voForSinglePushAndroid =  TestUtil.voForSinglePush;
	MsgFromKafkaVo voForSinglePushIos =  TestUtil.voForIOS;

	MsgFromKafkaVo voForSingleSMS = TestUtil.voForSingleSMS;
	
	AndroidVo androidVo = TestUtil.androidVo;
	IOSVo iosVo = TestUtil.iosVo;
	
	UserInfoOnPush userInfoOnPushWithYesForNotification = TestUtil.userInfoOnPushWithYes;

	UserInfoOnPush userInfoOnPushWithNoForNotification = TestUtil.userInfoOnPushWithNo;

	@AfterEach
	public void cleanUp() {
		Mockito.reset(mapper);
}

	@Test
	@DisplayName("MsgFromKafka Vo 객체를 Android 전송 용 객체로 Parsing 한다. - DB 모델 확정에 따른 추가 개발 후 진행")
	public void test() {
		assertTrue(false);
		manager.parseAndroid(voForSinglePushAndroid);
	}
	
	@Test
	@DisplayName("MsgFromKafka Vo 객체를 IOS 전송 용 객체로 Parsing 한다.  - DB 모델 확정에 따른 추가 개발 후 진행")
	public void test2() {
		assertTrue(false);
		manager.parseIos(voForSinglePushIos);

	}
	
	@Test
	@DisplayName("AndroidVo를 fcm으로 보내고 성공 메시지를 받는다.")
	public void test3() {
		ResultOfPush result =  manager.sendPush(instance, androidVo);
	}
	
	@Test
	@DisplayName("AndroidVo를 fcm으로 보내고 실패 메시지를 받는다.")
	public void test3_1() {
		ResultOfPush result = manager.sendPush(instance, androidVo);
	}
	
	@Test
	@DisplayName("IOSVo를 fcm으로 보내고 성공 메시지를 받는다.")
	public void test4() {
		ResultOfPush result = manager.sendPush(instance, iosVo);
		
	}
	
	@Test
	@DisplayName("IOSVo를 fcm으로 보내고 실패 메시지를 받는다.")
	public void test4_1() {
		ResultOfPush result = manager.sendPush(instance, iosVo);
		
	}
	
}
