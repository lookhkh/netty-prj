package com.kafka.kafkanetty.client.test.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import util.MsgFromKafkaAndroid;
import util.MsgFromKafkaSmss;
import util.TestUtil;

/**
 * FCM 연동 절차
 * 
 * 1. 구글 인증 절차 진행 후 auth Key 생성
 * 2.FCM 용 메시지 생성
 * 3. FMC 전송 
 * 4. 응답에 따른 추가 절차 진행
 * 
 * 
 * **/
@DisplayName("PUSH를 위한 구글 인증 진행 및 타입, 종류에 따른 메시지 전송 테스트")
public class PushHandlerTest {
	
	FirebaseMessaging instance = TestUtil.instance;
	SmsPushMapper mapper = TestUtil.mapper;
	

	SendManager mng = TestUtil.pushSingle;
	
	MsgFromKafkaVo voForMultipleAndroidPush =  MsgFromKafkaAndroid.voForMultiplePushWithValidDataBody;
	
	MsgFromKafkaVo voForSingleAndroidPush =  MsgFromKafkaAndroid.voForSinglePushWithValidDataBody;
	
	MsgFromKafkaVo voForMultipleSMS = MsgFromKafkaSmss.voForMultipleSMSWithValidDataBody;
	
	MsgFromKafkaVo voForSingleSMS = MsgFromKafkaSmss.voForSingleSmsWithValidDataBody;
	


	
	@AfterEach
	public void cleanUp() {
		Mockito.reset(mapper);

	}
	
//	@Test
//	@DisplayName("PushSingleManager에 넣은 사용자가 수신 여부를 N로 할 경우 vailidationMng가 던진 UserNotAllowNotification을 잡지 않고 위로 던진다")
//	public void test2() {
//		
//	
//	 Mockito.doThrow(UserNotAllowNotificationException.class).when(validMng).validSingleUserInfo(voForSingleAndroidPush.getTarget().get(0));
//	  assertThrows(UserNotAllowNotificationException.class,()->mng.send(voForSingleAndroidPush)); 
//
//	}
//	
//
//	@Test
//	@DisplayName("PushSingleManager에 넣은 사용자의 정보가 invalid 할 경우, vailidationMng가 던진 UserInfoInvalidException을 잡지 않고 위로 던진다")
//	public void test2_1() {
//		
//	
//	 Mockito.doThrow(UserInfoInvalidException.class).when(validMng).validSingleUserInfo(voForSingleAndroidPush.getTarget().get(0));
//	  assertThrows(UserInfoInvalidException.class,()->mng.send(voForSingleAndroidPush)); 
//
//	}
//	

}
