package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.kt.onnuipay.client.handler.manager.abstracts.PushManagerAbstract;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.IOSVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.MobileAbstractVo;

/*
 * KT OnnuriPay version 1.0
 *
 *  Copyright ⓒ 2022 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */
@Profile(value= {"default", "local","prod"})
@Component
public class SendPushManagerImpl extends PushManagerAbstract {

	@Autowired
	private Environment env;
	
	
	@Override
	public ResultOfPush sendPush(FirebaseMessaging instance, AndroidVo smsVo) {
		
		ResultOfPush result;
		
		Object msg = convertMobileVoToMessage(smsVo);
		
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
	
	
	/**
	 * TODO VO를 변환하는 로직 작성 220610 조현일
	 * 
	 * **/
	



	/**
	 * @apiNote profile 환경에 따라 Dry Run 여부 선택
	 * @param instance FCM 연동 객체
	 * @param smsVo FCM에 보낼 데이터 객체
	 * @param dryRun FCM에 SEND할 때 테스트 여부, true 시 dryRun
	 * @param vo send 결과에 따라 반환
	 * 
	 * **/
	@VisibleForTesting
	private ResultOfPush send(FirebaseMessaging instance, Object msg, boolean dryRun, MobileAbstractVo vo) {
		ResultOfPush result = null;
		
		try {
			
			if(!vo.isSingle()) {
				
				/**
				 * TODO Multicast send 기능 구현
				 * 
				 * **/
				
				BatchResponse results =  instance.sendMulticast((MulticastMessage) msg);
				result = ResultOfPush.builder()
							.id(null)
							.success(true)
							.vo(vo.getVo())
							.build();
			}else {
				
			
			
			String successfullId = instance.send((Message) msg,dryRun);
			result = ResultOfPush.builder()
						.id(successfullId)
						.success(true)
						.vo(vo.getVo())
						.build();
			}
						
		} catch (FirebaseMessagingException e) {
			
			e.printStackTrace();
			
			
			/**l
			 * TODO Firebase 연동 규격 확인 및 상황에 따라 예외처리 혹은 return 처리 220610 조현일
			 * 
			 * **/
			
			result = ResultOfPush.builder()
					    .id(e.getMessage())
					    .success(false)
					    .vo(vo.getVo())
					    .reason(e)
					    .build();
			
		} catch(Exception e) {
			
			result = ResultOfPush.builder()
				    .id(e.getMessage())
				    .success(false)
				    .vo(vo.getVo())
				    .reason(e)
				    .build();
		
		
		}
		
		return result;
	}


	
}
