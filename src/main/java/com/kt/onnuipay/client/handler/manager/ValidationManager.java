package com.kt.onnuipay.client.handler.manager;

import com.kt.onnuipay.kafka.kafkanetty.exception.UserInfoInvalidException;
import com.kt.onnuipay.kafka.kafkanetty.exception.UserNotAllowNotificationException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

/**
 * @apiNote 유저 정보 에러 혹은 Push 알람 수신 N로 설정되어 있을 시, mongoDb로 바로 실패 메시지 전송
 * 
 * **/

/*
 * KT OnnuriPay version 1.0
 *
 *  Copyright ⓒ 2022 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */

public interface ValidationManager {



	/**
	 * 
	 * @param MsgFromKafkaVo vo vo객체의 targets의 요소를 확인하여 Push 알림 여부를 확인 
	 * @return MsgFromKafkaVo 알림, DB에 저장된 유저정보가 적절치 않은 경우, 해당 유저 정보를 삭제한 이후, 입력 vo와 동일한 인스턴스를 반환
	 */
	public MsgFromKafkaVo validMultiUserInfo(MsgFromKafkaVo vo);


	public void validSingleUserInfo(String userToken) throws UserInfoInvalidException, UserNotAllowNotificationException;

}
