package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "Message")
public class Message {
	public static final String R_RN = "TempReceiveNumber";
	public static final String R_A = "TempAttachment";

	@JacksonXmlProperty(localName = R_RN)
	private String tempReceiveNumber;

	@JacksonXmlProperty(localName = "Subject")
	private String subject;

	@JacksonXmlProperty(localName = "Content")
	private String content;

	@JacksonXmlProperty(localName = "FileSize")
	private String fileSize;

	@JacksonXmlProperty(localName = R_A)
	private String tempAttachment;

	@Builder
	public Message(String tempReceiveNumber, String subject, String content, String fileSize, String tempAttachment) {
		this.tempReceiveNumber = tempReceiveNumber;
		this.subject = subject;
		this.content = content;
		this.fileSize = fileSize;
		this.tempAttachment = tempAttachment;
	}
	
	
}
