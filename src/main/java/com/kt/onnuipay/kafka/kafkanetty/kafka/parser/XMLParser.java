package com.kt.onnuipay.kafka.kafkanetty.kafka.parser;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;

import datavo.msg.MessageWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class XMLParser {
	
	private final XmlMapper mapper;

	public String parseToString(MessageWrapper vo) {
		log.info("XML parser received {}",vo);

		try {
			return mapper.writeValueAsString(vo);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new JsonDataProcessingWrapperException("XML Serialzing Error", e);
		}
	}
	
	public <T> T deserialzeFromJson(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new JsonDataProcessingWrapperException("XML DeSerialzing Error", e);
		}
	}

}
