package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO DB 스키마 활용하여 유저에게 PUSH를 보낼 때 어떤 정보를 활용할지 확인 220610
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

@Data
@AllArgsConstructor
public class UserInfoOnPush {

	private boolean pushYn;
	
	public boolean getPushYn() {

		return this.pushYn;
	}


	public boolean validation() {
		// TODO DB에서 가져온 유저 정보 Validation 로직 추가. 220610 조현일
		return true;
	}

}
