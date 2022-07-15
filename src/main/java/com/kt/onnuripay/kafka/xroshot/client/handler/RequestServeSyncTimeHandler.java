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
package com.kt.onnuripay.kafka.xroshot.client.handler;

import org.springframework.stereotype.Service;

import com.kt.onnuripay.common.config.vo.XroshotParameter;
import com.kt.onnuripay.kafka.xroshot.kafka.model.xml.Mas;
import com.kt.onnuripay.kafka.xroshot.kafka.model.xml.XMLConstant;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("request_server_time_handler")
@Sharable
@Slf4j
@AllArgsConstructor
public class RequestServeSyncTimeHandler extends ChannelInboundHandlerAdapter {

	private final XroshotParameter param;
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		if(log.isDebugEnabled()) log.debug("{} handler removed ",ctx.channel());
		super.handlerRemoved(ctx);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		Mas req = Mas.builder()
				.method(XMLConstant.REQ_AUTH)
				.serviceProviderID(param.getServiceProviderId())
				.build();
		
		
		ctx.writeAndFlush(req).addListener(new DefaultChannelHandlerListener(this));
	}
	
	
}
