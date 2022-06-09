package com.kafka.kafkanetty.client.test.handler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.client.handler.manager.impl.PushSingleManager;
import com.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kafka.kafkanetty.exception.UserNotAllowNotificationException;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

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

	SendManager mng = new PushSingleManager(instance,mapper);
	
	MsgFromKafkaVo voForMultiplePush =  TestUtil.voForMultiplePush;
	
	MsgFromKafkaVo voForSinglePush =  TestUtil.voForSinglePush;
	
	MsgFromKafkaVo voForMultipleSMS = TestUtil.voForMultipleSMS;
	
	MsgFromKafkaVo voForSingleSMS = TestUtil.voForSingleSMS;
	
	
	
	
	@Test
	@DisplayName("PushSingleManager에 SMS VO를 넣을 경우 혹은 MultiplePushVo를 넣을 경우 IllegalArgument Exception이 터진다.")
	/*
	 * https://firebase.google.com/docs/cloud-messaging/auth-server 
	 * 
	 * */
	public void test() {
		
	 Mockito.when(mapper.getIfSendYnByUserNo(ArgumentMatchers.anyString())).thenReturn(true);

		
		
	  assertThrows(IllegalArgumentException.class, ()->mng.send(voForMultipleSMS));
	  assertThrows(IllegalArgumentException.class, ()->mng.send(voForSingleSMS));
	  assertThrows(IllegalArgumentException.class, ()->mng.send(voForMultiplePush));

	  assertDoesNotThrow(()->mng.send(voForSinglePush)); 

	}
	
	@Test
	@DisplayName("PushSingleManager에 넣은 사용자가 수신 여부를 N로 할 경우 UserNotAllowNotification Exception이 터진다")
	public void test2() {
		
	 Mockito.when(mapper.getIfSendYnByUserNo(ArgumentMatchers.anyString())).thenReturn(false); //유저가 수신 여부를 N으로 했을 경우

	  assertThrows(UserNotAllowNotificationException.class,()->mng.send(voForSinglePush)); 

	}
	
	

	

}
