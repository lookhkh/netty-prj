package com.kt.onnuipay.kafka.kafkanetty.kafka.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.DataBody;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaMsgParserImpl implements KafkaMsgParser {

	private ObjectMapper mapper = new ObjectMapper();
	
	
		@Override
		public MsgFromKafkaVo parse(String msg)   {
		
		log.info("MsgParser Recived {}",msg);
			
		return tryConvertMsgOrThrow(msg); 
		

		}
		
		@Override
		public void setObjectMapper(ObjectMapper mapper) {
			this.mapper = mapper;
		}


		private MsgFromKafkaVo tryConvertMsgOrThrow(String msg) {
			
			MsgFromKafkaVo convertedValue = null;
			
			try {
				convertedValue = mapper.readValue(msg, MsgFromKafkaVo.class);
				
			} catch (JacksonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new JsonDataProcessingWrapperException("Message Json Parsing error",e);
			} catch(Exception e) {
				throw new RuntimeException("Unknown Error Happend originMsgIs=> "+msg,e);
			}
			return convertedValue;
		}
		
		

}
