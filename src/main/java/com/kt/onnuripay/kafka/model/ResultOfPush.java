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
package com.kt.onnuripay.kafka.model;

import datavo.MetaData;
import datavo.msg.MessageWrapper;
import lombok.Builder;
import lombok.Data;

@Data
public class ResultOfPush {
	
	
	private final MetaData metaData;
	private final boolean isSuccess;
	private final MessageWrapper vo;
	private final Throwable reason;

	@Builder
	public ResultOfPush(MetaData metaData,boolean isSuccess, MessageWrapper vo, Throwable reason) {
		this.metaData = metaData;
		this.isSuccess = isSuccess;
		this.vo = vo;
		this.reason = reason;
	}
	
	

}
