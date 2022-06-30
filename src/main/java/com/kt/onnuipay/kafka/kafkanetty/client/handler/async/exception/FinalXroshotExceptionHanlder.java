package com.kt.onnuipay.kafka.kafkanetty.client.handler.async.exception;

import org.springframework.stereotype.Component;

@Component
public class FinalXroshotExceptionHanlder implements AsyncExceptionHanlder<Throwable, Void> {

	@Override
	public Void handlerException(Throwable t) {
		
		t.printStackTrace();
		
		return null;
	}
}
