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
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.BaseXMLResponse;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.auth.AuthInfoVo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.ping.PingResponse;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.send.MessageSendRequestResult;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.send.ReportOfWholeSendMessages;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.serverInfo.SmsPushServerInfoVo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.serverTime.ServerTimeVo;

import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Sharable
@Slf4j
@AllArgsConstructor
@Service
public class MessageDecoderTo extends MessageToMessageDecoder<ByteBuf> {

	private final XMLParser parser;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		
		
		
			String msgJson = convertingByteBufToString(msg);
						
			Class<?> clazz = getClassFromMethod(extractMethodFromServer(msgJson));
			
			
			BaseXMLResponse baseVo = validationResponse(clazz, msgJson);
			
			out.add(baseVo);
			
			if(log.isDebugEnabled()) log.debug("Message decoded from {} to {} ", msgJson, clazz.toString());
		
		}
	
	/**
	 * 
	 * @param msg
	 * @return bytebuf를 string으로 변환한 이후, 뒤에 "</MAS>"를 붙인다.
	 * @apiNote Xroshot에서 xml을 연속해서 보내는 경우가 있어, 잊전 핸들러에서 </MAS>를 기준으로 분리하게 된다. 이에 잘린 부분을 다시 붙인다. 
	 */
    private String convertingByteBufToString(ByteBuf msg) {
        return (msg.toString(CharsetUtil.UTF_8)+XMLConstant.ENDING_MAS).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
         
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
		if(extractMethodFromServer.equals(XMLConstant.RES_PING)) return PingResponse.class;
		if(extractMethodFromServer.equals(XMLConstant.RES_SEND_MESSAGE)) return MessageSendRequestResult.class;
		if(extractMethodFromServer.equals(XMLConstant.RES_SEND_MESSAGE_ALL)) return ReportOfWholeSendMessages.class;
		throw new XroshotRuntimeException("Message Deserialization Error Message => "+extractMethodFromServer, extractMethodFromServer);
		
	}

	private String extractMethodFromServer(String msgJson) {
		String pipeStr = msgJson.substring(msgJson.indexOf("method")+"method=\"".length());
		String method = pipeStr.substring(0,pipeStr.indexOf("\""));
	
		return method;
	}
	


}
