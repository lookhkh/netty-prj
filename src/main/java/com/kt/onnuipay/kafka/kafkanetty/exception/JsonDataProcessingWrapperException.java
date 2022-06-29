package com.kt.onnuipay.kafka.kafkanetty.exception;

import lombok.Data;


@Data
public class JsonDataProcessingWrapperException extends RuntimeException {
	
	private final String errorMsg;
	private final Throwable e;

	public JsonDataProcessingWrapperException(String string, Exception e2) {
		super(string);
		this.errorMsg = string;
		this.e = e2;
	}

}
