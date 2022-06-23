package com.kt.onnuipay.kafka.kafkanetty.kafka.parser;

import org.springframework.stereotype.Component;

import com.google.gson.JsonSyntaxException;
import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;

import datavo.msg.MessageWrapper;
import datavo.msg.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaMsgParserImpl<T> implements KafkaMsgParser {

	
	@Override
	public MessageWrapper parse(String msg) throws JsonDataProcessingWrapperException {

		return tryConvertMsgOrThrow(msg, MessageWrapper.class);
		
	}

	private <T> T tryConvertMsgOrThrow(String msg, Class<T> type) {
			
		try {
			return  MessageUtils.toWrapper(msg, type);
				
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				throw new JsonDataProcessingWrapperException("Message Json Parsing error",e);
			} 
			catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Unknown Error Happend originMsgIs=> "+msg,e);
			}
		}
}
