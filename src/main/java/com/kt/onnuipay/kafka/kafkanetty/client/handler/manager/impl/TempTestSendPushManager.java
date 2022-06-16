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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@Component
public class TempTestSendPushManager extends PushManagerAbstract {

	@Override
	public ResultOfPush sendPush(FirebaseMessaging instance, AndroidVo smsVo) {
		log.info("{},{}",smsVo,instance);
		log.info(" = ======= =================================================================================================== ");
		log.info(" = ======= ==================================Mocking for Waiting for the response from server=============================================== ");

		int ran = new Random().nextInt(1000);
		
		try {
			Thread.currentThread().sleep(ran);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info(" = ======= ==================================Mocking server response {}=============================================== ", ran%2);
		
		
		if(ran % 2 ==0) {
			return ResultOfPush.builder()
					.id(smsVo.getVo().getSender())
					.success(true)
					.vo(smsVo.getVo())
					.build();

		}else {
			return ResultOfPush.builder()
					.id(smsVo.getVo().getSender())
					.success(false)
					.vo(smsVo.getVo())
					.build();
		}
		
		
			}
	
	@Override
	public ResultOfPush sendPush(FirebaseMessaging instance, IOSVo smsVo) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
