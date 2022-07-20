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
package com.kt.onnuripay.message.kafka.xroshot.kafka.model.xml.response.sub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import lombok.Builder;
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
