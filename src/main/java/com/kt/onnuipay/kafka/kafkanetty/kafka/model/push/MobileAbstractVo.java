package com.kt.onnuipay.kafka.kafkanetty.kafka.model.push;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.Getter;

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


@Getter
public abstract class MobileAbstractVo {
	
	private final MsgFromKafkaVo vo;
	

	public MobileAbstractVo(MsgFromKafkaVo vo) {
		this.vo = vo;
	}
	
	public String getTitle() {
		return vo.getPayload().get(0).getTitle();
	}
	
	public String getBody() {
		return vo.getPayload().get(0).getBody();

	}
	
	/**
	 * 
	 * @return 0 = single, 1 = multiple
	 * 
	 * **/
	public boolean isSingle() {
		return this.vo.getCodeOfType()==0;
	}
	
	
}
