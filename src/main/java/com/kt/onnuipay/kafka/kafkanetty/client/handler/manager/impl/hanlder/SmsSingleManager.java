package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import org.springframework.stereotype.Component;

import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import datavo.msg.MessageWrapper;
import io.netty.channel.Channel;
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

//	private final AsyncHttpClient client;
	private final XMLParser parser;
//	private final XroshotParameter param;
//	
//	private final ParsingServerResponse paringMsgServerInfo;
//	private final PrepareAndStartNettyClient prepareeAndStartClient;

    private final Channel xroshotChannel;
	
	
	@Override
	public void send(MessageWrapper vo) {
		log.info("SMS single Msg {}",vo);
		
		
		
		
		String serializedBody = parser.parseToString(vo.getSmsVo());
		
//		
//		Request r = new RequestBuilder()
//				.setUrl(param.getSendServerUrl())
//				.setMethod("get")
//				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
//				.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE)
//				.build();
//		
//		ListenableFuture<Response> f = client.executeRequest(r);
//
//		f.toCompletableFuture()
//			.thenApply(res -> res.getResponseBody(CharsetUtil.UTF_8))
//			.thenApply(paringMsgServerInfo::execute)
//			.thenAccept(prepareeAndStartClient::execute)
//			;
			
	}

	

}
