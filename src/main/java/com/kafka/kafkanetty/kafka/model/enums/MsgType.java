package com.kafka.kafkanetty.kafka.model.enums;

public enum MsgType {

	SMS("sms"),LMS("lms"),APP_PUSH("app_push");
	
	private final String name;

	private MsgType(String name) {
		this.name = name;
	}
}
