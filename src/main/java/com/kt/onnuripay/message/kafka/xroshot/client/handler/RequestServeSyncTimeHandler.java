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
package com.kt.onnuripay.message.kafka.xroshot.client.handler;

import org.springframework.stereotype.Service;

import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.kafka.xroshot.client.channelmanager.XroshotChannelManager;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.ServerTimeVo;
import com.kt.onnuripay.message.util.LoggerUtils;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
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
		LoggerUtils.logDebug(log, "{} handler removed ", ctx.channel()); 
		super.handlerRemoved(ctx);
	}
	
	
	/**
	 * 
	 * @implSpec 채널이 Active된 이후, Xroshot 서버로 서버 시간 동기화를 위한 시간을 요청한 이후, 요청이 성공하면 pipeline에서 스스로를 삭제함.
	 * 
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		Mas req = Mas.builder()
				.method(XMLConstant.REQ_AUTH)
				.serviceProviderID(param.getServiceProviderId())
				.build();
		
		
		ctx.writeAndFlush(req);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	    if(msg instanceof ServerTimeVo) {
	        LoggerUtils.logDebug(log, "Request Server Sync Handler Received Msg ", msg);
	        
	        ServerTimeVo vo = (ServerTimeVo)msg;
	        vo.checkResultAndThrowIfInvalidData(vo);
	        
	        LoggerUtils.logDebug(log, "ServerTime is valid, so removed this requestserversync Handler {}", vo);
	        
	        cleanUp(ctx, vo);
	        
	    }else {
	        super.channelRead(ctx, msg);
	    }
	}
    
	/**
	 * 
	 * @apiNote Channel의 상태 및 다음 동작을 위한 마무리 작업
	 * 
	 */
	private void cleanUp(ChannelHandlerContext ctx, ServerTimeVo vo) {
        ctx.channel().attr(XroshotChannelManager.KEY).set(XroshotChannelManager.REQ_SERVER_TIME);
        ctx.pipeline().remove(this);
        ctx.fireChannelRead(vo);
    }
	
	
}
