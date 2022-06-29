package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Component;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.XroshotAsyncHandler;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import datavo.msg.MessageWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


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

@Slf4j
@Component("sms-single-manager")
@AllArgsConstructor
public class SmsSingleManager implements SendManager {

	private final AsyncHttpClient client;
	private final XMLParser parser;
	private final XroshotParameter param;
	
	@Override
	public void send(MessageWrapper vo) {
		log.info("SMS single Msg {}",vo);
		
		String serializedBody = parser.parseToString(vo);
		
		Request r = new RequestBuilder()
				.setUrl(param.getSendServerUrl())
				.setMethod("get")
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_UTF_8)
				.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_UTF_8)
				.setBody(serializedBody)
				.build();
		
		ListenableFuture<Response> f = client.executeRequest(r);

		f.toCompletableFuture()
			.thenApply(response -> response.getResponseBody());
		
	}

}
