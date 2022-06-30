package com.kt.onnuipay.kafka.kafkanetty.client.handler.async.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.kt.onnuipay.client.ClientBootStrap;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.MessageDecoderTo;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.RequestServeSyncTimeHandler;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.AbstractAsyncXroshotHandler;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.Mas;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.ResourceInfo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.ServerTimeVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.SmsPushServerInfoVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.xml.XmlFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

@Component
public class GetServerSyncTime extends AbstractAsyncXroshotHandler<CompletableFuture<SmsPushServerInfoVo>, Void, Mas> {

	private final XroshotParameter param;
	private final ClientBootStrap boot;
	private final XMLParser parser;
	
	public GetServerSyncTime(XMLParser parser, ClientBootStrap boot, XroshotParameter param) {
		super(parser);
		this.param = param;
		this.boot = boot;
		this.parser = parser;
	}

	@Override
	public CompletableFuture<Void> execute(CompletableFuture<SmsPushServerInfoVo> target) {

		 target
			.thenApply(vo -> vo.getResource())
			.thenApply(this::startTransaction);
		 
		 return null;
		
	}
	
	private Channel startTransaction(ResourceInfo resource) {
		
		Mas req = Mas.builder()
					.method(XMLConstant.REQ_AUTH)
					.serviceProviderID(param.getServiceProviderId())
					.build();
		
		return boot.start(
						this.createInitHandler(this.toJson(req), parser), 
						resource.getAddress(), 
						resource.getPort());
		
		
		
				
	}

	private ChannelInitializer<SocketChannel> createInitHandler(String request, XMLParser parser) {
		// TODO Auto-generated method stub
		return new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(new LoggingHandler());
				p.addLast(new XmlFrameDecoder(Integer.MAX_VALUE));
				p.addLast(XMLConstant.DECODER_SERVER_TIME, new MessageDecoderTo<ServerTimeVo>(parser, ServerTimeVo.class));
				p.addLast(XMLConstant.REQUEST_SERVER_TIME,new RequestServeSyncTimeHandler(request));
			}
		};
	}
}
