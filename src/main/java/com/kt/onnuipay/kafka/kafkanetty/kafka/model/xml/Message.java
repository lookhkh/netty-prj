package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml;

import java.util.List;

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

	@JacksonXmlProperty(localName = "ReceiveNumber")
	private List<String> receiveNumber;

	@JacksonXmlProperty(localName = "Subject")
	private String subject;
	
	@JacksonXmlProperty(localName = "Content")
	private String content;


	@Builder
	public Message(List<String> receiveNumber, String subject, String content) {
		

		this.receiveNumber = receiveNumber;
		this.subject = subject;
		this.content = content;
	}
	
	
}
