package com.kafka.kafkanetty.kafka.model;

import lombok.Builder;
import lombok.Data;

@Data
public class ResultOfPush {
	
	private final boolean success; 
	private final String id;
	private final MsgFromKafkaVo vo;

	@Builder
	public ResultOfPush(boolean success, String id, MsgFromKafkaVo vo) {
		this.success = success;
		this.id = id;
		this.vo = vo;
	}
	
	

}
