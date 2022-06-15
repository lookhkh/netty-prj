package com.kt.onnuipay.kafka.kafkanetty.kafka.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;


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

@Data
public class DataBody {

	private final String title;
	private final String body;
	
	@JsonCreator
	@Builder
	public DataBody(@JsonProperty("title") String title, 
			        @JsonProperty("body") String body) {
		this.title = title;
		this.body = body;
	}
	
	
	
}
