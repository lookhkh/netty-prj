package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "MAS")
@ToString
public class ServerTimeVo {

	@JacksonXmlProperty(isAttribute = true, localName = "method")
	private final String methodName;
	@JacksonXmlProperty(localName = "Result")
	private final String result;
	@JacksonXmlProperty(localName = "Time")
	private final String time;
	
	@Builder
	public ServerTimeVo(@JsonProperty("method") String methodName, @JsonProperty("Result")String result, @JsonProperty("Time")String time) {
		this.methodName = methodName;
		this.result = result;
		this.time = time;
	}
	
	
}
