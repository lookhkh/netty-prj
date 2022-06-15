package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kt.onnuipay.client.handler.manager.ValidationManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kt.onnuipay.kafka.kafkanetty.exception.UserInfoInvalidException;
import com.kt.onnuipay.kafka.kafkanetty.exception.UserNotAllowNotificationException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/*
 * 
 * TODO 어떤 데이터를 활용해서 DB에서 정보를 가져올 것인가? 220610 조현일
 * 
 * 
 * 
 * */

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

@Service
@AllArgsConstructor
@Slf4j
public class ValidationManagerImpl implements ValidationManager {

	private final SmsPushMapper  mapper;
	private final TempMongodbTemplate mongo;
	
	@Override
	public MsgFromKafkaVo validMultiUserInfo(MsgFromKafkaVo vo)
			throws UserInfoInvalidException, UserNotAllowNotificationException {
		List<String> sanitizedTargetList = new ArrayList<>();
		
		
		int originNumberOfTargetLists = vo.getTarget().size();
		int numberOfsanitizedTarget = 0;
		/**
		 * TODO VO로부터 UserRegistration Tokens를 가져온 이후, 각각 밸리데이션 진행 후 실패한 리스트는 에러 처리
		 * 아래는 Example code 220613 조현일
		 * **/
		/* ------------------------------------------- */
		List<String> list = vo.getTarget();
		/* ------------------------------------------- */
		
		for(String userToken : list) {
			try {
				
				validSingleUserInfo(userToken);
				sanitizedTargetList.add(userToken);
				
			}catch(UserNotAllowNotificationException | UserInfoInvalidException e){
				log.warn("user {} validation error happend",vo,e);
				numberOfsanitizedTarget++;
			}
		}
		
		
		log.warn("Original number of target lists -> {}, number of sanitizedTargetList -> {}",originNumberOfTargetLists, numberOfsanitizedTarget);
		vo.setTarget(sanitizedTargetList);
		
		
		return vo;
		
	
	}
	
	@Override
	public void validSingleUserInfo(String userToken) 
			throws UserInfoInvalidException, UserNotAllowNotificationException {
	
		
		UserInfoOnPush info = mapper.getIfSendYnByUserNo(userToken);
		
		if(!info.validation()) {
			
			log.info("[{}] User validation Error",userToken);

			
			mongo.insertDbHistory(ResultOfPush.builder()
									.success(false)
									.reason( new UserInfoInvalidException("Validation Result -> invalid "+info, info))
									.build());
			
			throw new UserInfoInvalidException("infomation of {} is invalid", info);
		}
		
		if(!info.getPushYn()) {
			
			
			log.info("[{}] User not allow to get notified ",userToken);

			/**
			 * TODO PUSH 단걸 발생 실패 처리. 사유 : USER 알람 수신 N 220609 조현일
			 * 
			 * **/
			
			mongo.insertDbHistory(ResultOfPush.builder()
					.success(false)
					.reason(new UserNotAllowNotificationException(userToken+" not allow to get notified", userToken))
					.build());

			throw new UserNotAllowNotificationException("User "+info+" not allow to get Notified");
			
		}
	}
	
	
	

}
