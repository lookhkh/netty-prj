package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kt.onnuipay.kafka.kafkanetty.exception.XroshotRuntimeException;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "RCP")
@ToString
public class SmsPushServerInfoVo extends BaseXMLResponse{


	@JacksonXmlProperty(localName = "Resource")
	private final ResourceInfo resource;

	@Builder
	public SmsPushServerInfoVo(@JsonProperty("Result") String result, @JsonProperty("method") String method,  @JsonProperty("Resource")ResourceInfo resource) {
		super(method,result);

		this.resource = resource;
	}
	
	@Override
	public boolean valid() throws XroshotRuntimeException {
		// TODO Auto-generated method stub
		return this.resource.valid();
	}

}
