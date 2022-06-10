package com.kafka.kafkanetty.kafka.model.enums;

public enum TypeOfSending {

	SINGLE(0),  MULTIPLE(1);
	
	private final int code;

	private TypeOfSending(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}	
	
	
}
