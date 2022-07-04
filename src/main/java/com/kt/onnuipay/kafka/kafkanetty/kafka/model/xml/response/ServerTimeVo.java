package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kt.onnuipay.kafka.kafkanetty.exception.XroshotRuntimeException;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "MAS")
@ToString
public class ServerTimeVo extends BaseXMLResponse{


	@JacksonXmlProperty(localName = "Time")
	private final String time;
	
	@Builder
	public ServerTimeVo(@JsonProperty("method") String methodName, @JsonProperty("Result")String result, @JsonProperty("Time")String time) {
		super(methodName,result);
		this.time = time;
	}
	
	@Override
	public boolean valid() throws XroshotRuntimeException {
		// TODO Auto-generated method stub
		return !this.time.isBlank();
	}
	
	
}
