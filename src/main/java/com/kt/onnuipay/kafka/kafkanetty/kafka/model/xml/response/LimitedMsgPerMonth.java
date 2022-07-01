package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@AllArgsConstructor
@Builder
public class LimitedMsgPerMonth {

	@JacksonXmlProperty(isAttribute = true, localName = "msgType")
	private final int type;
	@JacksonXmlText
	private final String limit;
}
