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
package com.kt.onnuripay.message.kafka.parser;

import com.kt.onnuripay.datavo.msg.MessageWrapper;
import com.kt.onnuripay.message.common.exception.JsonDataProcessingWrapperException;


/**
 * 
 * TODO MSG 포맷 결정 되면 파서 만들기 220608 조현일 => (Solved) Producer에서 알아서 객체를 생성해주면, 컨슈머 쪽에서는 단순히 역직렬화만 수행
 * 
 * **/


/**
 * 
 * @author Cho hyun il lookhkh37@daonlink.com
 *
 */
public interface KafkaMsgParser {


	/**
	 * 
	 * @param msg Kafka Broker로부터 가져온 JSON String
	 * @return 
	 * @return SingleMessageWrapper Parsing 결과 VO
	 * @throws JsonDataProcessingWrapperException JSON Parsing 실패 시, RuntimeException을 extend 한 JsonDataProcessingWrapperException를 던진다
	 */
	public MessageWrapper parse(String msg) throws JsonDataProcessingWrapperException, RuntimeException;


	
}
