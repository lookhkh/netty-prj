package com.kafka.kafkanetty.client.test.manager.pushmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.client.handler.manager.SendPushManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.SendPushManagerImpl;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.DataBody;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.IOSVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.MobileAbstractVo;

import util.AndroidVos;
import util.IOSVos;
import util.MsgFromKafkaAndroid;
import util.MsgFromKafkaIOS;
import util.MsgFromKafkaSmss;
import util.TestUtil;
import util.UserInfos;

/**
 * @implNote com.kafka.kafkanetty.client.handler.manager.SendPushManager 테스트 케이스 모음 <br>
 * 1. Vo 객체를 실제 FCM로 보내기 위하여 ANDROID, IOS에 따른 객체 Parsing <br>
 * 2. 변환된 객체를 FCM으로 전송 및 결과 확인 <br>
 * 3. 예외 상황 처리 및 결과 return <br>
 * 
 * **/
@DisplayName("SMS PUSH manager 테스트, 직접적 외부연동 객체를 다룸")
public class SmsPushManageTest {


	SendPushManager managerMock = Mockito.mock(SendPushManager.class);
	SendPushManager manager = new SendPushManagerImpl();

	FirebaseMessaging instance = TestUtil.instance;
	SmsPushMapper mapper = TestUtil.mapper;

	SendManager mng = TestUtil.pushSingle;
	
	MsgFromKafkaVo voForSinglePushAndroid =  MsgFromKafkaAndroid.voForSinglePushWithValidDataBody;
	MsgFromKafkaVo voForSinglePushIos =  MsgFromKafkaIOS.voForSinglePushWithValidDataBody;

	MsgFromKafkaVo voForSingleSMS = MsgFromKafkaSmss.voForSingleSmsWithValidDataBody;
	
	AndroidVo androidVoMock = AndroidVos.androidVoMock;
	IOSVo iosVoMock = IOSVos.iosVoMock;
	
	UserInfoOnPush userInfoOnPushWithYesForNotification = UserInfos.userInfoOnPushWithYes;

	UserInfoOnPush userInfoOnPushWithNoForNotification = UserInfos.userInfoOnPushWithNo;

	@AfterEach
	public void cleanUp() {
		Mockito.reset(mapper);
}

	@Test
	@DisplayName("MsgFromKafka Vo 객체를 Android 전송 용 객체로 Parsing 한다.")
	public void test() {
		AndroidVo vo =  manager.parseAndroid(voForSinglePushAndroid);
		
		seeIfDataBodyInMobileVoIsSameWithOrginalMobileVO(vo);

	}

	@Test
	@DisplayName("MsgFromKafka Vo 객체를 IOS 전송 용 객체로 Parsing 한다.  - DB 모델 확정에 따른 추가 개발 후 진행")
	public void test2() {
		IOSVo vo =  manager.parseIos(voForSinglePushAndroid);
		
		seeIfDataBodyInMobileVoIsSameWithOrginalMobileVO(vo);

	}
	
	private void seeIfDataBodyInMobileVoIsSameWithOrginalMobileVO(MobileAbstractVo vo) {
		
		DataBody data = vo.getVo().getPayload().get(0);

		
		assertNotNull(vo);
		assertEquals(data.getTitle(), vo.getTitle());
		assertEquals(data.getBody(), vo.getBody());
	}
	
	
	@Test
	@DisplayName("AndroidVo를 fcm으로 보내고 성공 메시지를 받는다.")
	public void test3() {
		ResultOfPush result =  managerMock.sendPush(instance, androidVoMock);
	}
	
	@Test
	@DisplayName("AndroidVo를 fcm으로 보내고 실패 메시지를 받는다.")
	public void test3_1() {
		ResultOfPush result = managerMock.sendPush(instance, androidVoMock);
	}
	
	@Test
	@DisplayName("IOSVo를 fcm으로 보내고 성공 메시지를 받는다.")
	public void test4() {
		ResultOfPush result = managerMock.sendPush(instance, iosVoMock);
	}
	
	@Test
	@DisplayName("IOSVo를 fcm으로 보내고 실패 메시지를 받는다.")
	public void test4_1() {
		ResultOfPush result = managerMock.sendPush(instance, iosVoMock);
	}
	
}
