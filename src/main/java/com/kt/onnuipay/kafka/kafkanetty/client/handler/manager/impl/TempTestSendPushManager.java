package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kt.onnuipay.client.handler.manager.abstracts.PushManagerAbstract;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;

import datavo.msg.MessageWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * TEST를 위한 MOCK Manager
 * 
 * **/
@Profile("test")
@Slf4j
@Component
public class TempTestSendPushManager extends PushManagerAbstract {



	public void execute(FirebaseMessaging instance, MessageWrapper smsVo) {
		log.info("{},{}",smsVo,instance);
		log.info(" = ======= =================================================================================================== ");
		log.info(" = ======= ==================================Mocking for Waiting for the response from server=============================================== ");

		int ran = new Random().nextInt(10000);
		
		try {
			Thread.currentThread().sleep(ran);
			log.info(" = ======= ==================================Mocking server response {}=============================================== ", ran%2);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	

		}

	@Override
	public void sendPush(FirebaseMessaging instance, MessageWrapper smsVo) {
	}
	
	
	
}
