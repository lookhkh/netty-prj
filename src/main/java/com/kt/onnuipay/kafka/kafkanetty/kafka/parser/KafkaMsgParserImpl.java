package com.kt.onnuipay.kafka.kafkanetty.kafka.parser;

import org.springframework.stereotype.Component;

import com.google.gson.JsonSyntaxException;
import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;

import datavo.msg.MessageWrapper;
import datavo.msg.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaMsgParserImpl implements KafkaMsgParser {

	
	@Override
	public MessageWrapper parse(String msg) throws JsonDataProcessingWrapperException, RuntimeException {
		
		
		try {
		    return MessageUtils.toWrapper(msg, MessageWrapper.class);
		}catch(JsonSyntaxException es) {
		    log.error("Message Parsing error. error orgin => {}",msg);
		    throw new JsonDataProcessingWrapperException("역직렬화 에러 발생", es);
		}
		

	}

	
}
