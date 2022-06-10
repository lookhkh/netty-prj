package com.kafka.kafkanetty.client.handler.manager;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kafka.kafkanetty.kafka.model.push.IOSVo;


/**
 * @apiNote FirebaseMsgInstance와 각 Vo를 통하여 데이터를 실제로 보내는 역할을 한다.
 * 
 * **/
public interface SendPushManager {


	public AndroidVo parseAndroid(MsgFromKafkaVo vo);

	public IOSVo parseIos(MsgFromKafkaVo vo);

	public ResultOfPush sendPush(FirebaseMessaging instance, AndroidVo smsVo);

	public ResultOfPush sendPush(FirebaseMessaging instance, IOSVo smsVo);

	


}
