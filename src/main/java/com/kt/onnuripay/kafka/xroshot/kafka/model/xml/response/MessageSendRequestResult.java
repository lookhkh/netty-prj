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
package com.kt.onnuripay.kafka.xroshot.kafka.model.xml.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kt.onnuripay.common.exception.XroshotRuntimeException;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "MAS")
public class MessageSendRequestResult extends BaseXMLResponse {

	@JacksonXmlProperty(localName = "Time")
	private final String time;
	
	@JacksonXmlProperty(localName = "CustomMessageID")
	private final String customMessageID;
	
	@JacksonXmlProperty(localName = "Count")
	private final long count;
	
	@JacksonXmlProperty(localName = "GroupID")
	private final String groupId;
	
	@JacksonXmlProperty(localName = "JobID")
	private final String jobId;
	
	@JacksonXmlProperty(localName = "SequenceNumber")
	private final long sequenceNumber;

	
	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public MessageSendRequestResult(
			@JsonProperty("method") String methodName, 
			@JsonProperty("Result")String result, 
			@JsonProperty("Time")String time,
			@JsonProperty("CustomMessageID") String customMessageID,
			@JsonProperty("Count") long count,
			@JsonProperty("GroupID") String groupId,
			@JsonProperty("JobID") String jobId,
			@JsonProperty("SequenceNumber") long sequenceNumber) {
		
		super(methodName,result);
		
		this.time = time;
		this.customMessageID = customMessageID;
		this.count = count;
		this.groupId = groupId;
		this.jobId = jobId;
		this.sequenceNumber = sequenceNumber;
	}

	@Override
	public boolean valid() throws XroshotRuntimeException {
		return true;
	}
	
	
}
