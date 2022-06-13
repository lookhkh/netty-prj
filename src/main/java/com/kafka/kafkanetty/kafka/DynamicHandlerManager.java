package com.kafka.kafkanetty.kafka;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.exception.UserInfoInvalidException;
import com.kafka.kafkanetty.exception.UserNotAllowNotificationException;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

import lombok.extern.slf4j.Slf4j;


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

			
		} 
		
		catch(Exception e) {
			
			log.info("{}, unknown error happend",e.getMessage(),e);
			
			mongo.insertDbHistory(ResultOfPush.builder()
									.success(false)
									.reason(e.getCause())
									.build());

			
		}
		return result;
		
		

		
	}

	

}
