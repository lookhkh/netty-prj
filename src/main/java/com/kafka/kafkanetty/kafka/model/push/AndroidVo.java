package com.kafka.kafkanetty.kafka.model.push;

import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.Builder;

public class AndroidVo extends MobileAbstractVo {
	
	
	@Builder
	public AndroidVo(MsgFromKafkaVo vo) {
		super(vo);
	}


}
