package com.kafka.kafkanetty.client.handler.manager;

import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

public interface SendManager {

	public void send(MsgFromKafkaVo vo);


	
}
