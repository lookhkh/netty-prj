package com.kafka.kafkanetty.client.handler.manager.impl;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kafka.kafkanetty.exception.UserNotAllowNotificationException;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * TODO 단건 발송 요청 유저의 알림 수신 여부 체크 로직 추가 필요 220609 조현일
 * 
 * **/
@Setter
@AllArgsConstructor
@Component("push-single-manager")
@Slf4j
public class PushSingleManager implements SendManager {
	
	private final FirebaseMessaging instance;
	private final SmsPushMapper  mapper;
	

	@Override
	public void send(MsgFromKafkaVo vo) {
		log.info("PushSingleSendManager received {}",vo);
		if(vo.getCodeOfType() == 1 || vo.getTypeValue() == 2) {
			throw new IllegalArgumentException("PushSingleSendManager Only take Push Type and Single Type Vo => {}"+vo);
		}
		
		/*
		 * 
		 * TODO 유저 ID 가져오기
		 * 
		 * */
		String userNo = "TempuserNo"; // vo.getUserNo();
		
		boolean result = mapper.getIfSendYnByUserNo(userNo);
		
		if(!result) {
			/**
			 * TODO PUSH 단걸 발생 실패 처리. 사유 : USER 알람 수신 N
			 * 
			 * **/
			
			throw new UserNotAllowNotificationException(userNo+" not allow to get notified");
		}
		
		
	}

	
}
