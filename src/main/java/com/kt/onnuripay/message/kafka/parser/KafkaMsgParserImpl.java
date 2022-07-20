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

import org.springframework.stereotype.Component;

import com.google.gson.JsonSyntaxException;
import com.kt.onnuripay.datavo.msg.MessageWrapper;
import com.kt.onnuripay.datavo.msg.util.MessageUtils;
import com.kt.onnuripay.message.common.exception.JsonDataProcessingWrapperException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaMsgParserImpl implements KafkaMsgParser {

	
	@Override
	public MessageWrapper parse(String msg) throws JsonDataProcessingWrapperException, RuntimeException {
		
		
		try {
		    return MessageUtils.toWrapper(msg, MessageWrapper.class);
		}catch(JsonSyntaxException es) {
		    log.error("Message Parsing error. error orgin => {}",msg,es);
		    throw new JsonDataProcessingWrapperException("역직렬화 에러 발생", es);
		}
		

	}

	
}
