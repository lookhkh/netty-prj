package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.client.handler.manager.SendPushManager;
import com.kt.onnuipay.client.handler.manager.ValidationManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.IOSVo;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * TODO 단건 발송 요청 유저의 알림 수신 여부 체크 로직 추가 필요 220609 조현일
 * 
 * **/


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

@Setter
@AllArgsConstructor
@Component("push-single-manager")
@Slf4j
public class PushSingleManager implements SendManager {
	
	private final FirebaseMessaging instance;
	private final SendPushManager manager;
	private final ValidationManager validMng;

	@Override
	@Transactional
	public ResultOfPush send(MsgFromKafkaVo vo) {
		log.info("PushSingleSendManager received {}",vo);

		validMng.validSingleUserInfo(vo.getTarget().get(0));
				
		return manager.execute(instance,vo);

		
		
	}
	
}
