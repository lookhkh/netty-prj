package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import org.springframework.stereotype.Component;

import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;


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

@Component("sms-single-manager")
public class SmsSingleManager implements SendManager {

	@Override
	public ResultOfPush send(MsgFromKafkaVo vo) {
		return null;
		// TODO Auto-generated method stub
		
		
	}
}
