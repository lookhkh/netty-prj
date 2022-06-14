package com.kafka.kafkanetty.kafka.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
public class DataBody {

	private final String title;
	private final String body;
	
	@JsonCreator
	@Builder
	public DataBody(@JsonProperty("title") String title, 
			        @JsonProperty("body") String body) {
		this.title = title;
		this.body = body;
	}
	
	
	
}
