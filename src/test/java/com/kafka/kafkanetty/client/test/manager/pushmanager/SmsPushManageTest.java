package com.kafka.kafkanetty.client.test.manager.pushmanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.kafka.consumer.controller.TestVos;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.client.handler.manager.SendPushManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.SendPushManagerImpl;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;

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


	TestVos vos = TestVos.getTestVos();
	
	SendPushManager managerMock = Mockito.mock(SendPushManager.class);
	SendPushManager manager = new SendPushManagerImpl();

	FirebaseMessaging instance = TestUtil.instance;
	SmsPushMapper mapper = TestUtil.mapper;

	SendManager mng = TestUtil.pushSingle;
	
	
	


	@AfterEach
	public void cleanUp() {
		Mockito.reset(mapper);
}


	
	
	@Test
	@DisplayName("AndroidVo를 fcm으로 보내고 성공 메시지를 받는다.")
	public void test3() {
		ResultOfPush result =  managerMock.sendPush(instance, vos.getVoForSingleAndroidPush());
	}
	
	@Test
	@DisplayName("AndroidVo를 fcm으로 보내고 실패 메시지를 받는다.")
	public void test3_1() {
		ResultOfPush result = managerMock.sendPush(instance, vos.getVoForMultipleAndroidPush());
	}
	
	@Test
	@DisplayName("IOSVo를 fcm으로 보내고 성공 메시지를 받는다.")
	public void test4() {
		ResultOfPush result = managerMock.sendPush(instance, vos.getVoForMultipleIOSPush());
	}
	

}
