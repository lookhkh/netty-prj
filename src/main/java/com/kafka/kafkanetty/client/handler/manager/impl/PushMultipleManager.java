package com.kafka.kafkanetty.client.handler.manager.impl;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("push-multiple-manager")
public class PushMultipleManager implements SendManager {

	private final FirebaseMessaging instance;

	@Override
	public void send(MsgFromKafkaVo vo) {
		// TODO Auto-generated method stub
		
	}
}
