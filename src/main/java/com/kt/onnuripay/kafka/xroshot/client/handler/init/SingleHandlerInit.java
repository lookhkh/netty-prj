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
package com.kt.onnuripay.kafka.xroshot.client.handler.init;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kt.onnuripay.kafka.xroshot.client.handler.SendSingleMessageHandler;
import com.kt.onnuripay.kafka.xroshot.client.handler.codec.MessageDecoderTo;
import com.kt.onnuripay.kafka.xroshot.kafka.model.xml.XMLConstant;

import datavo.msg.MessageWrapper;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.xml.XmlFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

@Service("single_hanlder_init")
public class SingleHandlerInit  {
	
	
	private final ChannelInboundHandler authTicketHandler;
	private final ChannelInboundHandler exceptionHospital;
	private final ChannelInboundHandler requestServerTimeHandler;
	private final MessageDecoderTo decoder;

	
	public SingleHandlerInit(
			@Qualifier("auth_ticket_handler") ChannelInboundHandlerAdapter authTicketHandler,
			@Qualifier("exception_hospital_handler") ChannelInboundHandler exceptionHospital,
			@Qualifier("request_server_time_handler") ChannelInboundHandler requestServerTimeHandler,
			MessageDecoderTo decoder
			) {
		
		this.authTicketHandler = authTicketHandler;
		this.exceptionHospital = exceptionHospital;
		this.requestServerTimeHandler = requestServerTimeHandler;
		this.decoder = decoder;
	}
	
	
	public 	ChannelInitializer<SocketChannel> getChannelInit(){
		
		return new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(new LoggingHandler(LogLevel.DEBUG));
				p.addLast(new XmlFrameDecoder(Integer.MAX_VALUE));
				p.addLast(decoder);
				p.addLast(requestServerTimeHandler);
				p.addLast(authTicketHandler);
				p.addLast(exceptionHospital);				
			}
		};
		
	}
	
	
}
