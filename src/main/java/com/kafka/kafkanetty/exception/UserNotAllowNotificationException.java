package com.kafka.kafkanetty.exception;

import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
public class UserNotAllowNotificationException extends RuntimeException {
	
	private final String msg;
	private MsgFromKafkaVo vo;

	public UserNotAllowNotificationException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public UserNotAllowNotificationException(String msg, MsgFromKafkaVo vo) {
		super(msg);
		this.msg = msg;
		this.vo = vo;
	}
	

	
	
	
	

}
