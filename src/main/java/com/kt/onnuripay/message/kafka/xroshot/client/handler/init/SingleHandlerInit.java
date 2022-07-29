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
package com.kt.onnuripay.message.kafka.xroshot.client.handler.init;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.RequestAuthTicketHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.RequestPingHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.codec.DefaultMessageToByteEncoder;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.codec.MessageDecoderTo;

import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

@Service("single_hanlder_init")
public class SingleHandlerInit  {
	
	
	private final ChannelInboundHandler exceptionHospital;
	private final ChannelInboundHandler requestServerTimeHandler;
	private final DefaultMessageToByteEncoder encoder;
	private final MessageDecoderTo decoder;

	private final XroshotParameter param;
	
	
	public SingleHandlerInit(			
			@Qualifier("exception_hospital_handler") ChannelInboundHandler exceptionHospital,
			@Qualifier("request_server_time_handler") ChannelInboundHandler requestServerTimeHandler,			
			MessageDecoderTo decoder,
			DefaultMessageToByteEncoder encoder,
			XroshotParameter param
			) {
		
		this.exceptionHospital = exceptionHospital;
		this.requestServerTimeHandler = requestServerTimeHandler;
		this.decoder = decoder;
		this.encoder = encoder;
		this.param = param;
	}
	
	
	public 	ChannelInitializer<SocketChannel> getChannelInit(){
		
		return new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(new LoggingHandler(LogLevel.DEBUG));
				p.addLast(encoder);
				p.addLast(decoder);
				p.addLast(new RequestPingHandler());
				p.addLast(requestServerTimeHandler);
				p.addLast(new RequestAuthTicketHandler(param));
				p.addLast(exceptionHospital);		
			
			}
		};
		
	}
	
	
}
