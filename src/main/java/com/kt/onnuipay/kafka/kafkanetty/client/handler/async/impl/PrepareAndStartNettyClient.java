package com.kt.onnuipay.kafka.kafkanetty.client.handler.async.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.kt.onnuipay.client.ClientBootStrap;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.AbstractAsyncXroshotHandler;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.codec.MessageDecoderTo;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.Mas;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.ResourceInfo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.SmsPushServerInfoVo;

import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.xml.XmlFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

@Component
public class PrepareAndStartNettyClient  {

	private final XroshotParameter param;
	private final ClientBootStrap boot;
	private final MessageDecoderTo decoder;
	private final ChannelInboundHandler authTicketHandler;
	private final ChannelInboundHandler exceptionHospital;
	private final ChannelInboundHandler requestServerTimeHandler;
	
	public PrepareAndStartNettyClient(
			ClientBootStrap boot, 
			XroshotParameter param,
			@Qualifier("auth_ticket_handler") ChannelInboundHandlerAdapter authTicketHandler,
			@Qualifier("exception_hospital_handler") ChannelInboundHandler exceptionHospital,
			@Qualifier("request_server_time_handler") ChannelInboundHandler requestServerTimeHandler,
			MessageDecoderTo decoder) {
		this.param = param;
		this.boot = boot;
		this.authTicketHandler = authTicketHandler;
		this.exceptionHospital = exceptionHospital;
		this.requestServerTimeHandler = requestServerTimeHandler;
		this.decoder = decoder;
	
	}

	
	public CompletableFuture<Void> execute(CompletableFuture<SmsPushServerInfoVo> target) {

		 target
			.thenApply(vo -> vo.getResource())
			.thenAccept(this::startTransaction);
		 
		 return null;
		
	}
	
	private void startTransaction(ResourceInfo resource) {
		

		 boot.start(
						this.createInitHandler(), 
						resource.getAddress(), 
						resource.getPort());
				
	}

	private ChannelInitializer<SocketChannel> createInitHandler(
			) {
		// TODO Auto-generated method stub
		
		
		
		return new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(new LoggingHandler(LogLevel.DEBUG));
				p.addLast(new XmlFrameDecoder(Integer.MAX_VALUE));
				p.addLast(XMLConstant.DECODER_SERVER_TIME, decoder);
				p.addLast(XMLConstant.REQUEST_SERVER_TIME, requestServerTimeHandler);
				p.addLast(XMLConstant.REQUEST_AUTH_TICKET, authTicketHandler);
				p.addLast(XMLConstant.EXCEPTION_HOSPITAL, exceptionHospital);
			}
		};
	}
}
