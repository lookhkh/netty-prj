package com.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.client.handler.manager.ValidationManager;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.model.ValidationFailInfo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * @see https://firebase.google.com/docs/cloud-messaging/send-message#java
 * @see Firebase SDK Batch Request can contains up to 500 registration tokens
 * **/
@AllArgsConstructor
@Component("push-multiple-manager")
@Slf4j
public class PushMultipleManager implements SendManager {

	private final FirebaseMessaging instance;
	private final ValidationManager validMng;

	@Override
	public ResultOfPush send(MsgFromKafkaVo vo) {

		log.info("pushMultipleManager received {}",vo);

	    validMng.validMultiUserInfo(vo);
		
		
		
		
		
		
		return null;
		// TODO Auto-generated method stub
		
		
		
		
	}
}
