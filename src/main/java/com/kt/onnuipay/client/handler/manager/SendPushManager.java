package com.kt.onnuipay.client.handler.manager;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.IOSVo;


/**
 * @apiNote FirebaseMsgInstance와 각 Vo를 통하여 데이터를 실제로 보내는 역할을 한다.
 * 
 * **/
public interface SendPushManager {


	public AndroidVo parseAndroid(MsgFromKafkaVo vo);

	public IOSVo parseIos(MsgFromKafkaVo vo);

	public ResultOfPush sendPush(FirebaseMessaging instance, AndroidVo smsVo);

	public ResultOfPush sendPush(FirebaseMessaging instance, IOSVo smsVo);

	
	/**
	 * @param MsgFromKafkaVo 메시지 내용 및 대상 리스트를 포함하는 VO
	 * @param FirebaseMessaging FCM과 통신할 수 있는 객체
	 * @return ResultOfPush 통신 결과
	 */
	public ResultOfPush execute(FirebaseMessaging instance, MsgFromKafkaVo vo);

	


}
