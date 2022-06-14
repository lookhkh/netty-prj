package com.kafka.kafkanetty.client.handler.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kafka.kafkanetty.client.handler.manager.ValidationManager;
import com.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kafka.kafkanetty.exception.UserInfoInvalidException;
import com.kafka.kafkanetty.exception.UserNotAllowNotificationException;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.model.ValidationFailInfo;
import com.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/*
 * 
 * TODO 어떤 데이터를 활용해서 DB에서 정보를 가져올 것인가? 220610 조현일
 * 
 * 
 * 
 * */
@Service
@AllArgsConstructor
@Slf4j
public class ValidationManagerImpl implements ValidationManager {

	private final SmsPushMapper  mapper;
	private final TempMongodbTemplate mongo;
	
	@Override
	public void validMultiUserInfo(MsgFromKafkaVo vo)
			throws UserInfoInvalidException, UserNotAllowNotificationException {
		
		/**
		 * TODO VO로부터 UserRegistration Tokens를 가져온 이후, 각각 밸리데이션 진행 후 실패한 리스트는 에러 처리
		 * 아래는 Example code 220613 조현일
		 * **/
		/* ------------------------------------------- */
		List<String> list = Arrays.asList("abc","edf");
		/* ------------------------------------------- */
		
		list.stream().forEach(item -> 
			{
					validSingleUserInfo(vo);
					
			});
		
		
		
	
	}
	
	@Override
	public void validSingleUserInfo(MsgFromKafkaVo vo) 
			throws UserInfoInvalidException, UserNotAllowNotificationException {
	
		
		UserInfoOnPush info = mapper.getIfSendYnByUserNo(vo);
		
		if(!info.validation()) {
			
			log.info("[{}] User validation Error",vo);

			
			mongo.insertDbHistory(ResultOfPush.builder()
									.success(false)
									.reason( new UserInfoInvalidException("Validation Result -> invalid "+info, info))
									.build());
			
			return; 
		}
		
		if(!info.getPushYn()) {
			
			
			log.info("[{}] User not allow to get notified ",vo);

			/**
			 * TODO PUSH 단걸 발생 실패 처리. 사유 : USER 알람 수신 N 220609 조현일
			 * 
			 * **/
			
			mongo.insertDbHistory(ResultOfPush.builder()
					.success(false)
					.reason(new UserNotAllowNotificationException(vo+" not allow to get notified", vo))
					.build());

			return; 
			
		}
	}
	
	
	

}
