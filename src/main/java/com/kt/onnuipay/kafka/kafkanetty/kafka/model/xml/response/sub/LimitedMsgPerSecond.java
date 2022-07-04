package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.sub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Builder
public class LimitedMsgPerSecond {

	@JacksonXmlProperty(isAttribute = true, localName = "msgType")
	private final int type;
	@JacksonXmlText
	@JacksonXmlProperty(localName = "limit")
	private final String target;
	
	public LimitedMsgPerSecond(@JsonProperty("msgType") int type,
			@JsonProperty("limit") String limit) {
		this.type = type;
		this.target = limit;
	}
	
	
	


	
}
