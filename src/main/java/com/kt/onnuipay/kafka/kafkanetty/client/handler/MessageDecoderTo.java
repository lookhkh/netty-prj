package com.kt.onnuipay.kafka.kafkanetty.client.handler;

import java.util.List;

import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class MessageDecoderTo<T> extends MessageToMessageDecoder<ByteBuf> {

	private final XMLParser parser;
	private final Class<T> type;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		T converted = parser.deserialzeFromJson(msg.toString(CharsetUtil.UTF_8), type);
		log.info("{} is decooded",converted);
		out.add(converted);
		log.info("well done");
	}

}
