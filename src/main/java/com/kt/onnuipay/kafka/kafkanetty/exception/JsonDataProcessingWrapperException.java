package com.kt.onnuipay.kafka.kafkanetty.exception;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.Data;


@Data
public class JsonDataProcessingWrapperException extends RuntimeException {
	
	private final String errorMsg;
	private final Throwable e;

	public JsonDataProcessingWrapperException(String string, IOException e) {
		super(string);
		this.errorMsg = string;
		this.e = e;
	}

}
