package com.kt.onnuipay.client.handler.manager;

import com.google.firebase.messaging.FirebaseMessaging;

import datavo.msg.MessageWrapper;


/**
 * @apiNote FirebaseMsgInstance와 각 Vo를 통하여 데이터를 실제로 보내는 역할을 한다.
 * 
 * **/
public interface SendPushManager {


	/**
	 * @param MsgFromKafkaVo 메시지 내용 및 대상 리스트를 포함하는 VO
	 * @param FirebaseMessaging FCM과 통신할 수 있는 객체
	 * @return ResultOfPush 통신 결과
	 */
	public void execute(FirebaseMessaging instance, MessageWrapper vo);




}
