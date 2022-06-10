package com.kafka.kafkanetty.exception;

import com.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class UserInfoInvalidException extends RuntimeException {
	
	private final UserInfoOnPush info;
	private final String msg;

	public UserInfoInvalidException(String msg, UserInfoOnPush info) {
		super(msg);
		this.info = info;
		this.msg = msg;
	}
	
	

	
}
