package com.kafka.kafkanetty.client.handler.manager.impl;

import org.springframework.stereotype.Component;

import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;

@Component("sms-single-manager")
public class SmsSingleManager implements SendManager {

	@Override
	public ResultOfPush send(MsgFromKafkaVo vo) {
		return null;
		// TODO Auto-generated method stub
		
		
	}
}
