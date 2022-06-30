package com.kt.onnuipay.kafka.kafkanetty.client.handler.async;

import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractAsyncXroshotHandler<T,V,Z> implements AsyncXroshotHanlder<T, V> {

	private final XMLParser parser;

	protected String toJson(Z object) throws JsonDataProcessingWrapperException  {
		return parser.parseToString(object);
	}
	
	protected Z deserialzeFromJson(String responseBody, Class<Z> clazz) throws JsonDataProcessingWrapperException {
		return parser.deserialzeFromJson(responseBody, clazz);
	}
}
