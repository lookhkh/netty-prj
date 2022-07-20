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
package com.kt.onnuripay.message.common.exception;

import lombok.Data;

@Data
public class ChannelHandlerExceptionError extends RuntimeException {

	private final String msg;
	private final Throwable cause;
	
	public ChannelHandlerExceptionError(String msg, Throwable cause) {
		super(msg);
		this.msg = msg;
		this.cause = cause;
	}

	
}
