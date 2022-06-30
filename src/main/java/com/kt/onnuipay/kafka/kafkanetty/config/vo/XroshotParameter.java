package com.kt.onnuipay.kafka.kafkanetty.config.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

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
 * @date 2022. 6. 29.
 * @apiNote Xroshot 관련 설정 정보, 스프링 싫행 시, bean으로 등록
 */

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "xroshot")
public class XroshotParameter {

	
	private final String sendServerUrl; //
	private final String serviceProviderId; //
	private final String serviceProviderPw; //
	private final String certFile; //
	private final String authKey; //
	private final String indexBegin; //
	private final String indexEnd; //
	private final String endUserId; //
	private final String oneTimeSecretKey; //
	private final String version; //
	
	
	public XroshotParameter(String sendServerUrl, String serviceProviderId, String serviceProviderPw, String certFile,
			String authKey, String indexBegin,  String endUserId, String oneTimeSecretKey,
			String version) {
		this.sendServerUrl = sendServerUrl;
		this.serviceProviderId = serviceProviderId;
		this.serviceProviderPw = serviceProviderPw;
		this.certFile = certFile;
		this.authKey = authKey;
		this.indexBegin = indexBegin;
		this.indexEnd = String.valueOf(Integer.valueOf(this.indexBegin)+128);
		this.endUserId = endUserId;
		this.oneTimeSecretKey = oneTimeSecretKey;
		this.version = version;
	}
	
	
}
