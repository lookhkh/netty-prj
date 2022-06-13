package com.kafka.kafkanetty.kafka.parser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.kafkanetty.exception.InvalidMsgFormatException;
import com.kafka.kafkanetty.kafka.model.DataBody;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaMsgParserImpl implements KafkaMsgParser {

	@Autowired
	private final ObjectMapper mapper;
	
	@Override
		public MsgFromKafkaVo parse(String msg) throws InvalidMsgFormatException, StreamReadException  {
		
		MsgFromKafkaVo convertedValue = mapper.readValue(msg.getBytes(), MsgFromKafkaVo.class);
		
		Map<Boolean, List<DataBody>> result = convertedValue.validateDataBodys();
		
		result.getOrDefault(false, Arrays.asList())
			.stream()
			.forEach(data -> log.warn("MalFormed Msg Type {}",data));
		
		convertedValue.setPayload(result.getOrDefault(true, Arrays.asList()));
		
		return convertedValue; 
		
		}

}
