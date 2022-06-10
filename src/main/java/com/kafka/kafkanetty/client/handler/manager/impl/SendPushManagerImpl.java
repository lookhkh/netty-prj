package com.kafka.kafkanetty.client.handler.manager.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.google.common.annotations.VisibleForTesting;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.kafka.kafkanetty.client.handler.manager.SendPushManager;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kafka.kafkanetty.kafka.model.push.IOSVo;
import com.kafka.kafkanetty.kafka.model.push.MobileAbstractVo;

public class SendPushManagerImpl implements SendPushManager {

	@Autowired
	private Environment env;
	
	/**
	 * TODO VO를 변환하는 로직 작성 220610 조현일
	 * 
	 * **/
	
	@Override
	public AndroidVo parseAndroid(MsgFromKafkaVo vo) {
		return AndroidVo.builder().vo(vo).build();
	}

	@Override
	public IOSVo parseIos(MsgFromKafkaVo vo) {
		return IOSVo.builder().vo(vo).build();
	}

	@Override
	public ResultOfPush sendPush(FirebaseMessaging instance, AndroidVo smsVo) {
		
		ResultOfPush result;
		
		Message msg = convertMobileVoToMessage(smsVo);
		
		if(env !=null) {
			List<String> list = Arrays
								.stream(env.getActiveProfiles())
								.collect(Collectors.toList());
			
			if(list.contains("prod")) {
				result = send(instance, msg, false,  smsVo);
			}else {
				result = send(instance, msg,true,  smsVo);
			}
			
		}else {
			result = send(instance, msg, true,  smsVo);	
		}
			
	
		return result;
	}



	
	@Override
	public ResultOfPush sendPush(FirebaseMessaging instance, IOSVo smsVo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@VisibleForTesting
	private Message convertMobileVoToMessage(AndroidVo smsVo) {
		/**
		 * TODO MSG 객체 생성 로직 추가 220610 조현일
		 * 
		 * **/
		Notification notification = Notification.builder().build();
		
		
		return 	Message.builder()
					.setNotification(notification)
					.build();

	}

	/**
	 * @apiNote profile 환경에 따라 Dry Run 여부 선택
	 * @param instance FCM 연동 객체
	 * @param smsVo FCM에 보낼 데이터 객체
	 * @param dryRun FCM에 SEND할 때 테스트 여부, true 시 dryRun
	 * @param vo send 결과에 따라 반환
	 * 
	 * **/
	@VisibleForTesting
	private ResultOfPush send(FirebaseMessaging instance, Message msg, boolean dryRun, MobileAbstractVo vo) {
		ResultOfPush result;
		try {
			String successfullId = instance.send(msg,dryRun);
			result = ResultOfPush.builder()
						.id(successfullId)
						.success(true)
						.vo(vo.getVo())
						.build();
						
		} catch (FirebaseMessagingException e) {
			
			e.printStackTrace();
			
			
			/**
			 * TODO Firebase 연동 규격 확인 및 상황에 따라 예외처리 혹은 return 처리 220610 조현일
			 * 
			 * **/
			
			result = ResultOfPush.builder()
					    .id(e.getMessage())
					    .success(false)
					    .vo(vo.getVo())
					    .build();
			
		}
		
		return result;
	}


	
}
