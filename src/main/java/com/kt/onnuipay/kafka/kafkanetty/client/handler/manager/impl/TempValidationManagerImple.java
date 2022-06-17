package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.kt.onnuipay.client.handler.manager.ValidationManager;
import com.kt.onnuipay.kafka.kafkanetty.exception.UserInfoInvalidException;
import com.kt.onnuipay.kafka.kafkanetty.exception.UserNotAllowNotificationException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.extern.slf4j.Slf4j;

@Profile("test")
@Component
@Slf4j
public class TempValidationManagerImple implements ValidationManager {

	@Override
	public void validSingleUserInfo(String userToken)
			throws UserInfoInvalidException, UserNotAllowNotificationException {
		
		log.info("input data => {}",userToken);
		
	}
	
	@Override
	public MsgFromKafkaVo validMultiUserInfo(MsgFromKafkaVo vo) {
		// TODO Auto-generated method stub
		log.info("input data => {}",vo);

		return vo;
	}
}
