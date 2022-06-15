package com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums;

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

public enum KafkaKeyEnum {

	ANDROID("android",0),IOS("ios",1),SMS("sms",2);
	
	private String type;
	private int code;
	
	private KafkaKeyEnum(String type, int code) {
		this.type = type;
		this.code  = code;
	}

	public int getTypeCode() {
		// TODO Auto-generated method stub
		return this.code;
	}
	
	
}
