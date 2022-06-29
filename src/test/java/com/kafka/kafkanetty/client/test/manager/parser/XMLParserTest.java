package com.kafka.kafkanetty.client.test.manager.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import datavo.msg.MessageWrapper;

public class XMLParserTest {

	XMLParser parser = new XMLParser(new XmlMapper());
	
	@Test
	@DisplayName("XML을 JSON으로 변환 중, 에러가 발생하면 런타임 에러를 던진다")
	public void test() {
		assertTrue(false);
		parser.parseToString(null);
	}
	
	@Test
	@DisplayName("JSON을 XML으로 변환 중, 에러가 발생하면 런타임 에러를 던진다")
	public void test1() {
		assertTrue(false);
		parser.deserialzeFromJson(null, MessageWrapper.class);
	}
}
