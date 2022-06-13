package com.kafka.kafkanetty.kafka.model;

import lombok.Builder;
import lombok.Data;

@Data
public class DataBody {

	private final String title;
	private final String body;
	
	@Builder
	public DataBody(String title, String body) {
		this.title = title;
		this.body = body;
	}
	
	
	
}
