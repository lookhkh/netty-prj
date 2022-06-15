package com.kt.onnuipay.kafka.kafkanetty.kafka.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.DataBody;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaMsgParserImpl implements KafkaMsgParser {

	private final ObjectMapper mapper;
	
	
	
	
	
	public KafkaMsgParserImpl(ObjectMapper mapper) {
		this.mapper = new ObjectMapper();
	}





		@Override
		public MsgFromKafkaVo parse(String msg)   {
		
		MsgFromKafkaVo convertedValue = null;
		
		try {
			convertedValue = mapper.readValue(msg, MsgFromKafkaVo.class);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException("Message Json Parsing error",e);
		} 
		
		
			Map<Boolean, List<DataBody>> result = convertedValue.validateDataBodys();
			
			result.getOrDefault(false, Arrays.asList())
				.stream()
				.forEach(data -> log.warn("MalFormed Msg Type {}",data));
			/**
			 * TODO MalFormed Msg 데이터 HIST에 저장하는 로직 추가 220614 조현일
			 * 
			 * **/
			
			convertedValue.setPayload(result.getOrDefault(true, Arrays.asList()));
			
			return convertedValue; 
			
		
		
		
		}

}
