package com.kafka.kafkanetty.client.handler.manager;

import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

/**
 * @apiNote 유저 정보 에러 혹은 Push 알람 수신 N로 설정되어 있을 시, mongoDb로 바로 실패 메시지 전송
 * 
 * **/
public interface ValidationManager {


	void validSingleUserInfo(MsgFromKafkaVo vo);


	void validMultiUserInfo(MsgFromKafkaVo vo);

}
