package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import java.util.concurrent.CompletableFuture;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Component;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.AsyncXroshotHanlder;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.exception.AsyncExceptionHanlder;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.exception.FinalXroshotExceptionHanlder;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.impl.GetServerSyncTime;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.impl.ParsingServerResponse;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.SmsPushServerInfoVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import datavo.msg.MessageWrapper;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class SmsSingleManager implements SendManager {

	private final AsyncHttpClient client;
	private final XMLParser parser;
	private final XroshotParameter param;
	
	private final ParsingServerResponse paringMsgServerInfo;
	private final GetServerSyncTime getSyncServerTime;

	
	private final FinalXroshotExceptionHanlder FinalXroshotExceptionHandler;
	
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
			.thenApply(res -> res.getResponseBody(CharsetUtil.UTF_8))
			.thenApply(paringMsgServerInfo::execute)
			.thenAccept(getSyncServerTime::execute);
			
	}

	

}
