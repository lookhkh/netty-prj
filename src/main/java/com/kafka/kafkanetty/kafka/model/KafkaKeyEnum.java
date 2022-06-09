package com.kafka.kafkanetty.kafka.model;

public enum KafkaKeyEnum {

	ANDROID("android",0),IOS("ios",1),SMS("sms",2);
	
	private String type;
	private int code;
	
	private KafkaKeyEnum(String type, int code) {
		this.type = type;
		this.code  = code;
	}

	int getTypeCode() {
		// TODO Auto-generated method stub
		return this.code;
	}
	
	
}
