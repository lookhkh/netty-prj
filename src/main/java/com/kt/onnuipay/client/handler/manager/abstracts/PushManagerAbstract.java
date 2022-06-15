package com.kt.onnuipay.client.handler.manager.abstracts;

import com.google.common.annotations.VisibleForTesting;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.kt.onnuipay.client.handler.manager.SendPushManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.IOSVo;

/**
 * 
 * @author Cho Hyun Il lookhkh37@daonlink.com
 * @apiNote Mobile Push Manager 객체 공통 
 */

public abstract class PushManagerAbstract implements SendPushManager {


	@Override
	public ResultOfPush execute(FirebaseMessaging instance, MsgFromKafkaVo vo) {
		ResultOfPush result;
		
		switch(vo.getTypeValue()) {
			case 0 : {
				/**
				 * 
				 * TODO ANDROID 용 통신 VO 만들기 220610 조현일
				 * 
				 * **/
				AndroidVo smsVo = this.parseAndroid(vo);
				result = this.sendPush(instance, smsVo);
				
				break;
			}
			case 1 : {
				/**
				 * 
				 * TODO IOS 용 통신 VO 만들기 220610 조현일
				 * 
				 * **/
				
				IOSVo smsVo = this.parseIos(vo);
				
				result = this.sendPush(instance, smsVo);
				
				
				break;
			}
			default : { //Type이 안드로이드, IOS가 아닌 경우, 에러를 던진다. 방어로직
				throw new IllegalArgumentException("IllegalArgument  "+vo);
			}
		}
		
		return result;		
	}
	
	/**
	 * 
	 * @implNote Android 메시지를 FCM에 보내고, 전송 결과를 리턴한다.
	 * 
	 */
	@Override
	public abstract ResultOfPush sendPush(FirebaseMessaging instance, AndroidVo smsVo) ;
	
	/**
	 * 
	 * @implNote IOS 메시지를 FCM에 보내고, 전송 결과를 리턴한다.
	 * 
	 */
	@Override
	public abstract ResultOfPush sendPush(FirebaseMessaging instance, IOSVo smsVo) ;
	
	
	@Override
	public AndroidVo parseAndroid(MsgFromKafkaVo vo) {
		return AndroidVo.builder().vo(vo).build();
	}
	
	@Override
	public IOSVo parseIos(MsgFromKafkaVo vo) {
		return IOSVo.builder().vo(vo).build();
	}
	
	
	@VisibleForTesting
	protected Object convertMobileVoToMessage(AndroidVo smsVo) {
		/**
		 * TODO MSG 객체 생성 로직 추가 220610 조현일
		 * 
		 * **/
		Notification notification = Notification.builder().build();
		
		if(smsVo.isSingle()) {
			return 	Message.builder()
						.setNotification(notification)
						.build();
			}
		else {
			return MulticastMessage.builder()
					.setNotification(notification)
					.build();
		}
	}
	
	@VisibleForTesting
	protected Object convertMobileVoToMessage(IOSVo smsVo) {
		/**
		 * TODO MSG 객체 생성 로직 추가 220610 조현일
		 * 
		 * **/
		Notification notification = Notification.builder().build();
		
		if(smsVo.isSingle()) {
			return 	Message.builder()
						.setNotification(notification)
						.build();
			}
		else {
			return MulticastMessage.builder()
					.setNotification(notification)
					.build();
		}
	
}
}
