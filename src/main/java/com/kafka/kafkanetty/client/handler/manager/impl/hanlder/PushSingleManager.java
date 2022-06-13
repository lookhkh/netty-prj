package com.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.client.handler.manager.SendPushManager;
import com.kafka.kafkanetty.client.handler.manager.ValidationManager;
import com.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kafka.kafkanetty.exception.UserInfoInvalidException;
import com.kafka.kafkanetty.exception.UserNotAllowNotificationException;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kafka.kafkanetty.kafka.model.push.IOSVo;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * TODO 단건 발송 요청 유저의 알림 수신 여부 체크 로직 추가 필요 220609 조현일
 * 
 * **/
@Setter
@AllArgsConstructor
@Component("push-single-manager")
@Slf4j
public class PushSingleManager implements SendManager {
	
	private final FirebaseMessaging instance;
	private final SendPushManager manager;
	private final ValidationManager validMng;

	@Override
	@Transactional
	public ResultOfPush send(MsgFromKafkaVo vo) {
		log.info("PushSingleSendManager received {}",vo);

		validMng.validSingleUserInfo(vo);
		
		ResultOfPush result;
		
		switch(vo.getTypeValue()) {
			case 0 : {
				/**
				 * 
				 * TODO ANDROID 용 통신 VO 만들기 220610 조현일
				 * 
				 * **/
				AndroidVo smsVo = manager.parseAndroid(vo);
				result = manager.sendPush(instance, smsVo);
				
				break;
			}
			case 1 : {
				/**
				 * 
				 * TODO IOS 용 통신 VO 만들기 220610 조현일
				 * 
				 * **/
				
				IOSVo smsVo = manager.parseIos(vo);
				result = manager.sendPush(instance, smsVo);
				
				break;
			}
			default : { //Type이 안드로이드, IOS가 아닌 경우, 에러를 던진다. 방어로직
				throw new IllegalArgumentException("IllegalArgument  "+vo);
			}
		}
		
		return result;
		
		
	}
	
}
