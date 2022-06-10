package com.kafka.kafkanetty.kafka;

import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.exception.UserInfoInvalidException;
import com.kafka.kafkanetty.exception.UserNotAllowNotificationException;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class DynamicHandlerManager {

	private final SendManager smsSingleMng;
	private final SendManager smsMultipleMng;
	private final SendManager pushSingleSend;
	private final SendManager pushMultipleSend;
	private final TempMongodbTemplate mongo;
	
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
