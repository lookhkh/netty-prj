package com.kafka.kafkanetty.client.handler.manager.impl;

import org.springframework.stereotype.Component;

import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

@Component("sms-single-manager")
public class SmsSingleManager implements SendManager {

	@Override
	public void send(MsgFromKafkaVo vo) {
		// TODO Auto-generated method stub
		
	}
}
