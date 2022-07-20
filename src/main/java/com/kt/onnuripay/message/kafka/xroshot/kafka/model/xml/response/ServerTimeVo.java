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
package com.kt.onnuripay.message.kafka.xroshot.kafka.model.xml.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kt.onnuripay.message.common.exception.XroshotRuntimeException;

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
		return !this.time.isBlank();
	}
	
	
}
