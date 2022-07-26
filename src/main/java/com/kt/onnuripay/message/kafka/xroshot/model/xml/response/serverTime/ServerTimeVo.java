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
package com.kt.onnuripay.message.kafka.xroshot.model.xml.response.serverTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.kt.onnuripay.message.common.exception.XroshotRuntimeException;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.BaseXMLResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
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
