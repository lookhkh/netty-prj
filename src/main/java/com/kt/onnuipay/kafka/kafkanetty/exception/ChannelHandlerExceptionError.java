package com.kt.onnuipay.kafka.kafkanetty.exception;

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
