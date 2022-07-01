package com.kt.onnuipay.kafka.kafkanetty.client.handler.codec;

import org.springframework.stereotype.Service;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.Mas;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class DefaultMessageToByteEncoder extends MessageToByteEncoder<Mas> {

	private final XMLParser parser;
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Mas msg, ByteBuf out) throws Exception {
		
		out.writeBytes(parser.parseToString(msg).getBytes(CharsetUtil.UTF_8));
		if(log.isDebugEnabled()) log.debug("MessageDecoder received {} and encoded it into byte",msg);
	}

	
	
}
