package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "RCP")
@ToString
public class SmsPushServerInfoVo {

	@JacksonXmlProperty(isAttribute = true, localName = "method")
	private final String methodName;
	@JacksonXmlProperty(localName = "Result")
	private final String result;
	@JacksonXmlProperty(localName = "Resource")
	private final ResourceInfo resource;

	@Builder
	public SmsPushServerInfoVo(@JsonProperty("Result") String result, @JsonProperty("method") String method,  @JsonProperty("Resource")ResourceInfo resource) {
		this.result = result;
		this.resource = resource;
		this.methodName = method;
	}
	
	
	
	public boolean valid() {
		return this.resource.valid() && this.result.equals(XMLConstant.OK);
	}



	
}
