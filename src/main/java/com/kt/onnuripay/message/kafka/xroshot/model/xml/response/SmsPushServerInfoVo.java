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
package com.kt.onnuripay.message.kafka.xroshot.model.xml.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kt.onnuripay.message.common.exception.XroshotRuntimeException;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
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
		return this.resource.valid();
	}

}
