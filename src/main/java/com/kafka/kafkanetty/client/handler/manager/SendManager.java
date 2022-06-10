package com.kafka.kafkanetty.client.handler.manager;

import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;

public interface SendManager {

	public ResultOfPush send(MsgFromKafkaVo vo);


	
}
