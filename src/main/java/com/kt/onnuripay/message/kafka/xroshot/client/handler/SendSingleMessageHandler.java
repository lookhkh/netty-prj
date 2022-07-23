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

import com.kt.onnuripay.datavo.msg.MessageWrapper;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 15.
 * @implNote 메시지를 전달하는 핸들러. 메시지 객체마다 새로이 생성 후 사용한다. @NotSharable
 *
 */

@AllArgsConstructor
@Slf4j
public class SendSingleMessageHandler extends ChannelInboundHandlerAdapter {

	private final MessageWrapper wrapper;
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		if(log.isDebugEnabled()) log.debug("{} handler removed ",ctx.channel());
		super.handlerRemoved(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	    if(log.isDebugEnabled()) log.debug("SendSingleHanlder Received AuthInfo {}", msg);
		
		
		
		Mas mas = createMasDependingOnMessage(wrapper);
		
		if(log.isDebugEnabled()) log.debug("메시지 생성 => {}",mas);
		
		ctx.writeAndFlush(mas);
		
		
		
		
		
	}

	private Mas createMasDependingOnMessage(MessageWrapper msgs) {

		/**
		 * TODO 메시지 타입에 따라서 Mas 빌드하기 220704 조현일
		 * 
		 * **/
		
		return Mas.builder().method(XMLConstant.REQ_SEND_MESSAGE_SINGLE).build();
	}
}
