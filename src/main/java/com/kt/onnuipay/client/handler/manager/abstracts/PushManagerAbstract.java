package com.kt.onnuipay.client.handler.manager.abstracts;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kt.onnuipay.client.handler.manager.SendPushManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;

import datavo.msg.MessageWrapper;

/**
 * 
 * @author Cho Hyun Il lookhkh37@daonlink.com
 * @apiNote Mobile Push Manager 객체 공통 
 */

public abstract class PushManagerAbstract implements SendPushManager {


	@Override
	public ResultOfPush execute(FirebaseMessaging instance, MessageWrapper vo) {
		ResultOfPush result;
		
		switch(vo.getTypeValue()) {
			case 0 : {
				/**
				 * 
				 * TODO ANDROID 용 통신 VO 만들기 220610 조현일
				 * 
				 * **/
				result = this.sendPush(instance, vo);
				
				break;
			}
			case 1 : {
				/**
				 * 
				 * TODO IOS 용 통신 VO 만들기 220610 조현일
				 * 
				 * **/
				
				
				result = this.sendPush(instance, vo);
				
				
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
	 * @implNote  메시지를 FCM에 보내고, 전송 결과를 리턴한다.
	 * 
	 */
	@Override
	public abstract ResultOfPush sendPush(FirebaseMessaging instance, MessageWrapper smsVo) ;
	

	

	
	
	
	

}
