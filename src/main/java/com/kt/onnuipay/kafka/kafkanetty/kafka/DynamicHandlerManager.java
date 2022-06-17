package com.kt.onnuipay.kafka.kafkanetty.kafka;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

import lombok.extern.slf4j.Slf4j;

/*
 * KT OnnuriPay version 1.0
 *
 *  Copyright ⓒ 2022 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */


@Slf4j
@Component
public class DynamicHandlerManager {

	@Qualifier(value = "sms-single-manager")
	private final SendManager smsSingleMng;
	
	@Qualifier(value = "sms-multiple-manager")
	private final SendManager smsMultipleMng;
	
	@Qualifier(value = "push-single-manager")
	private final SendManager pushSingleSend;
	
	@Qualifier(value = "push-multiple-manager")
	private final SendManager pushMultipleSend;
	
	TempMongodbTemplate mongo;
	
	
	public DynamicHandlerManager(
			@Qualifier(value = "sms-single-manager") SendManager smsSingleMng, 
			@Qualifier(value = "sms-multiple-manager") SendManager smsMultipleMng,	
			@Qualifier(value = "push-single-manager") SendManager pushSingleSend,
			@Qualifier(value = "push-multiple-manager") SendManager pushMultipleSend, 
			TempMongodbTemplate mongo) {
		this.smsSingleMng = smsSingleMng;
		this.smsMultipleMng = smsMultipleMng;
		this.pushSingleSend = pushSingleSend;
		this.pushMultipleSend = pushMultipleSend;
		this.mongo = mongo;
	}

	
	
	public ResultOfPush consume(MsgFromKafkaVo vo) {
		
		ResultOfPush result = null;
		
		try {
		
			if(vo.getCodeOfType() ==0 && vo.getTypeValue() == 2) { //단건 
				result = smsSingleMng.send(vo);
			}
			
			if(vo.getCodeOfType() ==1 && vo.getTypeValue() == 2) { //대량 SMS
				result = smsMultipleMng.send(vo);
			}
			
			if(vo.getCodeOfType() ==0 && vo.getTypeValue() != 2) { //단건 PUSH, 스켈레톤 구상 완료
				result = pushSingleSend.send(vo);
			}
			
			if(vo.getCodeOfType() ==1 && vo.getTypeValue() != 2) { //대량 PUSH
				result = pushMultipleSend.send(vo);
			}
			
			mongo.insertDbHistory(result);
			
			return result;

			
		} 
		
		catch(Exception e) {
			
			log.info("{}, unknown error happend",e.getMessage(),e);
			
			mongo.insertDbHistory(ResultOfPush.builder()
									.success(false)
									.reason(e.getCause())
									.build());
			
			throw new RuntimeException("unknown error happend",e);

			
		}
		
		

		
	}

	

}
