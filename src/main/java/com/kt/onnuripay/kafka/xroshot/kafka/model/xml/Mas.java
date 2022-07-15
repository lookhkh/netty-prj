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
package com.kt.onnuripay.kafka.xroshot.kafka.model.xml;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "MAS")
public class Mas {
	@JacksonXmlProperty(isAttribute = true, localName = "method")
	private String method;

	@JacksonXmlProperty(localName = "ServiceProviderID")
	private String serviceProviderID;
	
	@JacksonXmlProperty(localName = "Result")
	private String result;

	@JacksonXmlProperty(localName = "Time")
	private String time;

	@JacksonXmlProperty(localName = "EndUserID")
	private String endUserID;

	@JacksonXmlProperty(localName = "AuthTicket")
	private String authTicket;

	@JacksonXmlProperty(localName = "AuthKey")
	private String authKey;

	@JacksonXmlProperty(localName = "Version")
	private String version;

	@JacksonXmlProperty(localName = "SessionID")
	private String SessionID;

	@JacksonXmlProperty(localName = "MessageType")
	private String messageType;

	@JacksonXmlProperty(localName = "MessageSubType")
	private String messageSubType;

	@JacksonXmlProperty(localName = "CallbackNumber")
	private String callbackNumber;

	@JacksonXmlProperty(localName = "CustomMessageID")
	private String customMessageID;

	@JacksonXmlProperty(localName = "Filename")
	private String filename;

	@JacksonXmlProperty(localName = "FileSize")
	private String fileSize;

	@JacksonXmlProperty(localName = "Path")
	private String path;

	@JacksonXmlProperty(localName = "SequenceNumber")
	private String sequenceNumber;

	@JacksonXmlProperty(localName = "JobID")
	private String jobID;

	@JacksonXmlProperty(localName = "GroupID")
	private String groupID;
	
	@JacksonXmlProperty(localName = "Reason")
	private String reason;

	@JacksonXmlProperty(localName = "Message")
	private Message message;
	
	@JacksonXmlProperty(localName ="MessageBundle")
	private List<Message> bundle;

	@Builder
	public Mas(String method, String serviceProviderID, String result, String time,
			String endUserID, String authTicket, String authKey, String version, String sessionID, String messageType,
			String messageSubType, String callbackNumber, String customMessageID, String filename, String fileSize,
			String path, String sequenceNumber, String jobID, String groupID, Message message, String reason, List<Message> bundle) {
		super();
		this.method = method;
		this.serviceProviderID = serviceProviderID;
		this.result = result;
		this.time = time;
		this.endUserID = endUserID;
		this.authTicket = authTicket;
		this.authKey = authKey;
		this.version = version;
		SessionID = sessionID;
		this.messageType = messageType;
		this.messageSubType = messageSubType;
		this.callbackNumber = callbackNumber;
		this.customMessageID = customMessageID;
		this.filename = filename;
		this.fileSize = fileSize;
		this.path = path;
		this.sequenceNumber = sequenceNumber;
		this.jobID = jobID;
		this.groupID = groupID;
		this.message = message;
		this.reason = reason;
		this.bundle = bundle;
	}

}
