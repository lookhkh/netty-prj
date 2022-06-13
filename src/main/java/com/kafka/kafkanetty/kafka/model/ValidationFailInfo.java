package com.kafka.kafkanetty.kafka.model;

import lombok.Builder;

@Builder
public class ValidationFailInfo {

	private String id;
	private Throwable reason;
	
}
