package com.kt.onnuipay.kafka.kafkanetty.kafka.parser;

import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;

import datavo.msg.MessageWrapper;


/**
 * 
 * TODO MSG 포맷 결정 되면 파서 만들기 220608 조현일
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
	public MessageWrapper parse(String msg) throws JsonDataProcessingWrapperException;


	
}
