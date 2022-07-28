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

import org.springframework.stereotype.Service;

import com.kt.onnuripay.message.kafka.parser.XMLParser;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.util.LoggerUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Sharable
@AllArgsConstructor
@Service
@Slf4j
public class DefaultMessageToByteEncoder extends MessageToByteEncoder<Mas> {

	private final XMLParser parser;
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Mas msg, ByteBuf out) throws Exception {
		
		out.writeBytes(parser.parseToString(msg).getBytes(CharsetUtil.UTF_8));
		LoggerUtils.logDebug(log, "MessageEncoder received {} and encoded it into byte", msg); 
	}

	
	
}
