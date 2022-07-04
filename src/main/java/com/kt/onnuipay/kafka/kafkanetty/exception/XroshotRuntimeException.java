package com.kt.onnuipay.kafka.kafkanetty.exception;

import lombok.Getter;

@Getter
public class XroshotRuntimeException extends RuntimeException {

	private final Object r;
	
	public XroshotRuntimeException(String msg, Object target) {
		super(msg);
		this.r = target;
	}
	

	
}
