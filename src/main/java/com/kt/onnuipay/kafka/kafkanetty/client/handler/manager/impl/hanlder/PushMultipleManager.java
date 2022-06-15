package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.client.handler.manager.SendPushManager;
import com.kt.onnuipay.client.handler.manager.ValidationManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.IOSVo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * @see https://firebase.google.com/docs/cloud-messaging/send-message#java
 * @see Firebase SDK Batch Request can contains up to 500 registration tokens
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

@AllArgsConstructor
@Component("push-multiple-manager")
@Slf4j
public class PushMultipleManager implements SendManager {

	private final FirebaseMessaging instance;
	private final ValidationManager validMng;
	private final SendPushManager manager;

	@Override
	public ResultOfPush send(MsgFromKafkaVo vo) {

		log.info("pushMultipleManager received {}",vo);

		vo = validMng.validMultiUserInfo(vo);
		
		return manager.execute(instance,vo);
		
	}


}
