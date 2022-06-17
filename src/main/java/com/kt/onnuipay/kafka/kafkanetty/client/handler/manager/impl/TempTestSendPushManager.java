package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl;

import java.util.Random;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kt.onnuipay.client.handler.manager.abstracts.PushManagerAbstract;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.IOSVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.MobileAbstractVo;

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

	@Override
	public ResultOfPush sendPush(FirebaseMessaging instance, AndroidVo smsVo) {
		return execute(instance, smsVo);
		
		
			}
	
	@Override
	public ResultOfPush sendPush(FirebaseMessaging instance, IOSVo smsVo) {
		return execute(instance, smsVo);
	}

	private ResultOfPush execute(FirebaseMessaging instance, MobileAbstractVo smsVo) {
		log.info("{},{}",smsVo,instance);
		log.info(" = ======= =================================================================================================== ");
		log.info(" = ======= ==================================Mocking for Waiting for the response from server=============================================== ");

		int ran = new Random().nextInt(10000);
		
		try {
			Thread.currentThread().sleep(ran);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info(" = ======= ==================================Mocking server response {}=============================================== ", ran%2);
		
		
			return ResultOfPush.builder()
					.id(smsVo.getVo().getSender())
					.success(true)
					.vo(smsVo.getVo())
					.build();

		}
	
	
	
}
