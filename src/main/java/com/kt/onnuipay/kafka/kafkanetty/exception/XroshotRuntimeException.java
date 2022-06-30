package com.kt.onnuipay.kafka.kafkanetty.exception;

import org.asynchttpclient.Response;

import lombok.Getter;

@Getter
public class XroshotRuntimeException extends RuntimeException {

	private final String r;
	
	public XroshotRuntimeException(String msg, String target) {
		super(msg);
		this.r = target;
	}
	

	
}
