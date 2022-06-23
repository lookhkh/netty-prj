package com.kt.onnuipay.client.handler.manager.abstracts;

import java.util.concurrent.CompletableFuture;

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
	public void execute(FirebaseMessaging instance, MessageWrapper vo) {
		switch(vo.getTypeValue()) {
			case 0 : {

				this.sendPushForSingle(instance, vo);
			}
			case 1 : {

				this.sendPushForMulti(instance, vo);
				
			}
			default : { //Type이 안드로이드, IOS가 아닌 경우, 에러를 던진다. 방어로직
				throw new IllegalArgumentException("IllegalArgument  "+vo);
			}
		}
		
	}

	
	protected abstract void sendPushForSingle(FirebaseMessaging instance, MessageWrapper vo);
	
	protected abstract void sendPushForMulti(FirebaseMessaging instance, MessageWrapper vo);

	
	

}
