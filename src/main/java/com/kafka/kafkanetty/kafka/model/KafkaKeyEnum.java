package com.kafka.kafkanetty.kafka.model;

public enum KafkaKeyEnum {

	ANDROID("android"),IOS("ios"),SMS("sms");
	
	private String type;

	private KafkaKeyEnum(String type) {
		this.type = type;
	}
	
	
}
