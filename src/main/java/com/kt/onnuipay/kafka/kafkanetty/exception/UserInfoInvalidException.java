package com.kt.onnuipay.kafka.kafkanetty.exception;

import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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


@Slf4j
@Getter
public class UserInfoInvalidException extends RuntimeException {
	
	private final UserInfoOnPush info;
	private final String msg;

	public UserInfoInvalidException(String msg, UserInfoOnPush info) {
		super(msg);
		this.info = info;
		this.msg = msg;
	}
	
	

	
}
