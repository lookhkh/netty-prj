package com.kt.onnuipay.kafka.kafkanetty.kafka.parser;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.util.JsonMapper;
import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;

import datavo.MetaData;
import datavo.msg.MessageWrapper;
import datavo.msg.SingleMessageWrapper;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaMsgParserImpl implements KafkaMsgParser {

	ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public <T> T parse(String msg, Class<T> target) throws JsonDataProcessingWrapperException {
		log.info("MsgParser Recived Msg {}",msg);

		
		SingleMessageWrapper wr =  (SingleMessageWrapper)tryConvertMsgOrThrow(msg);
		System.out.println(wr);
	
		return null;
	}
	
	
	
	

	private Object tryConvertMsgOrThrow(String msg) {
			
		try {
			return  mapper.convertValue(msg, SingleMessageWrapper.class);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new JsonDataProcessingWrapperException("Message Json Parsing error",e);
			} 
			catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Unknown Error Happend originMsgIs=> "+msg,e);
			}
		}
}
