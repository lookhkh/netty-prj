package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kt.onnuipay.kafka.kafkanetty.exception.XroshotRuntimeException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;

import lombok.Getter;
import lombok.ToString;

/**
 * 
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


/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 4.
 * @apiNote XML Xroshot base vo class, 모든 하위 객체는 valid 메소드를 오버라이드 후, 적절한 로직을 추가한다.
 * 
 */

@Getter
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "MAS")
@ToString
public abstract class BaseXMLResponse {

	@JacksonXmlProperty(isAttribute = true, localName = "method")
	private final String methodName;
	@JacksonXmlProperty(localName = "Result")
	private final String result;
	public BaseXMLResponse(@JsonProperty("method") String methodName, @JsonProperty("Result")String result) {
		this.methodName = methodName;
		this.result = result;
	}
	
	/**
	 * 
	 * @param target 호출한 객체 자신을 넣는다.
	 * @throws XroshotRuntimeException
	 */
	public void checkResultAndThrowIfInvalidData(Object target) throws XroshotRuntimeException{
		if(!(this.result.equals(XMLConstant.OK) && this.valid())) throw new XroshotRuntimeException("Response vadlidation Error",target) ;
	}
	/**
	 * 
	 * @throws XroshotRuntimeException validation 후, 오류가 있을 경우, 해당 에러를 던지며, ErrorHospital에서 해당 에러를 잡는다.
	 */
	public abstract boolean valid() throws XroshotRuntimeException;
	
	
	
}
