package com.kafka.kafkanetty.exception;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class UserNotAllowNotificationException extends RuntimeException {
	
	private final String msg;

	public UserNotAllowNotificationException(String msg) {
		super();
		this.msg = msg;
	}
	
	
	
	
	
	

}
