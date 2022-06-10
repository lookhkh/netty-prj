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

	
	
	@SuppressWarnings("finally")
	public ResultOfPush consume(MsgFromKafkaVo vo) {
		
		ResultOfPush result = null;
		
		try {
		
			if(vo.getCodeOfType() ==0 && vo.getTypeValue() == 2) { //단건 SMS
				result = smsSingleMng.send(vo);
			}
			
			if(vo.getCodeOfType() ==1 && vo.getTypeValue() == 2) { //대량 SMS
				result = smsMultipleMng.send(vo);
			}
			
			if(vo.getCodeOfType() ==0 && vo.getTypeValue() != 2) { //단건 PUSH
				result = pushSingleSend.send(vo);
			}
			
			if(vo.getCodeOfType() ==1 && vo.getTypeValue() != 2) { //대량 PUSH
				result = pushMultipleSend.send(vo);
			}
			
		} catch(UserNotAllowNotificationException e) {
			/**
			 * 
			 * Push Notification 수신 여부를 N로 선택한 유저가 존재하여 메시지 발송 처리를 하지 않음.
			 * 
			 * 
			 * **/
			log.info("[{}] User not allow to get notified ",e.getVo(),e);
			
			/**
			 * 
			 * TODO DB history에 결과 입력하기 220610 조현일
			 * 
			 * **/


			
		
		} catch(UserInfoInvalidException e) {
			
			/**
			 * 
			 * 불러온 User 정보가 Invalid 한 경우 발생
			 * 
			 * 
			 * **/
			log.info("[{}] User not allow to get notified ",e.getInfo(),e);
			
			/**
			 * 
			 * TODO DB history에 결과 입력하기 220610 조현일
			 * 
			 * **/



		} catch(IllegalArgumentException e) {
			
			log.info("{}",e.getMessage(),e);
			
		}
		finally {
			
			log.info("result : {}",result);
			/**
			 * 
			 * TODO 로직이 상세화되면 공통 로직을 집어넣을 예정 220610 조현일
			 * 
			 * **/
			
			
			mongo.insertDbHistory(result);
			
			return result;


			
		}
		

		
	}

	

}
