package com.kafka.kafkanetty.kafka.model.push;

import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.Getter;

@Getter
public abstract class MobileAbstractVo {
	
	private final MsgFromKafkaVo vo;

	public MobileAbstractVo(MsgFromKafkaVo vo) {
		this.vo = vo;
	}
	
	
	
}
