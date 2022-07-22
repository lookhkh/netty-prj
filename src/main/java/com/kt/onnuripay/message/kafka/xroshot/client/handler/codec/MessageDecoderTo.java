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
package com.kt.onnuripay.message.kafka.xroshot.client.handler.codec;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kt.onnuripay.message.common.exception.XroshotRuntimeException;
import com.kt.onnuripay.message.kafka.parser.XMLParser;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.AuthInfoVo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.BaseXMLResponse;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.ServerTimeVo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.SmsPushServerInfoVo;

import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class MessageDecoderTo extends MessageToMessageDecoder<ByteBuf> {

	private final XMLParser parser;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		
		
		
			String msgJson = msg.toString(CharsetUtil.UTF_8);
			
			Class<?> clazz = getClassFromMethod(extractMethodFromServer(msgJson));
			
			String serializedMessage = msg.toString(CharsetUtil.UTF_8);
			
			BaseXMLResponse baseVo = validationResponse(clazz, serializedMessage);
			
			out.add(baseVo);
			
			if(log.isDebugEnabled()) log.debug("Message decoded from {} to {} with class {}",msg, msgJson, clazz);
		
		}

	private BaseXMLResponse validationResponse(Class<?> clazz, String serializedMessage) {
		Object base = parser.deserialzeFromJson(serializedMessage, clazz);
		
		if(!(base instanceof BaseXMLResponse)) throw new XroshotRuntimeException("parsing error happend at decoder. message info => "+serializedMessage, base);
		
		BaseXMLResponse baseVo = (BaseXMLResponse)base;
		
		baseVo.checkResultAndThrowIfInvalidData(baseVo);
		
		return baseVo;
	}

	private Class<?> getClassFromMethod(String extractMethodFromServer) {
		if(extractMethodFromServer.equals(XMLConstant.RES_SERVER_TIME)) return ServerTimeVo.class;
		if(extractMethodFromServer.equals(XMLConstant.RES_REGIST)) return AuthInfoVo.class;
		if(extractMethodFromServer.equals(XMLConstant.MESSAGE_INFO_REQUEST)) return SmsPushServerInfoVo.class;
		
		throw new XroshotRuntimeException("Message Deserialization Error Message => "+extractMethodFromServer, extractMethodFromServer);
	}

	private String extractMethodFromServer(String msgJson) {
		String pipeStr = msgJson.substring(msgJson.indexOf("method")+"method=\"".length());
		String method = pipeStr.substring(0,pipeStr.indexOf("\""));
	
		return method;
	}
	


}
