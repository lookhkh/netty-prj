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
package com.kt.onnuripay.kafka.parser;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kt.onnuripay.common.exception.JsonDataProcessingWrapperException;

import datavo.msg.MessageWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class XMLParser {
	
	private final XmlMapper mapper;

	public String parseToString(Object object) throws JsonDataProcessingWrapperException {
		log.info("XML parser received {}",object);

		try {
			return mapper.writeValueAsString(object)+"\0";
		} catch (JsonProcessingException e) {
			throw new JsonDataProcessingWrapperException("XML Serialzing Error", e);
		}
	}
	
	public  <T> T deserialzeFromJson(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new JsonDataProcessingWrapperException("XML DeSerialzing Error", e);
		}
	}
	
	 

}
