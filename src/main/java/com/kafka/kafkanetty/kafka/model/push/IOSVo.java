package com.kafka.kafkanetty.kafka.model.push;

import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.Builder;

public class IOSVo extends MobileAbstractVo {

	@Builder
	public IOSVo(MsgFromKafkaVo vo) {
		super(vo);
	}

	
}
