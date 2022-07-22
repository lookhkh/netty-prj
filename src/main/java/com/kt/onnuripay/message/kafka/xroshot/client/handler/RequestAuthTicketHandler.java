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
import com.kt.onnuripay.message.common.exception.RunTimeExceptionWrapper;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.util.NewXroshotAuth;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.ServerTimeVo;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 15.
 * @implSpec Xroshot으로부터 XML 형식으로 로그인을 요청
 *
 */

@Service(value = "auth_ticket_handler")
@Slf4j
@AllArgsConstructor
@Sharable
public class RequestAuthTicketHandler extends ChannelInboundHandlerAdapter {
	
	private final XroshotParameter param;

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		if(log.isDebugEnabled()) log.debug("{} RequestAuthTicketHandler removed",ctx.channel());
		super.handlerRemoved(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	    if(log.isDebugEnabled()) log.debug("received Msg from previous handler {}",msg);
		
		ServerTimeVo vo = (ServerTimeVo)msg;
		
		vo.checkResultAndThrowIfInvalidData(vo);
		
		Mas req = Mas.builder()
					.method(XMLConstant.REQ_REGIST)
					.serviceProviderID(this.param.getServiceProviderId())
					.endUserID(param.getEndUserId())
					.authTicket(createAuthTicket(vo,param))
					.authKey(param.getAuthKey())
					.version(param.getVersion())
					.build();
		
		if(log.isDebugEnabled()) log.debug("completed xml req -> {}",req.toString());
					
		ctx.writeAndFlush(req).addListener(new DefaultChannelHandlerListener(this));
		
		
		
	}

	private String createAuthTicket(ServerTimeVo vo, XroshotParameter param) {
		
		String tempPublicKey =param.getCertFile().substring(param.getIndexBegin(), param.getIndexEnd()) ;
		String tempAuthTicket = param.getServiceProviderId() + "|" + param.getServiceProviderPw() + "|" + param.getServiceProviderId() + "|" + vo.getTime() + "|" + param.getOneTimeSecretKey();

		try {
			return NewXroshotAuth.encrypto2(tempAuthTicket.getBytes(), tempPublicKey.getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RunTimeExceptionWrapper("Error Happend during generating authticket", vo, e);
		}
	}
}
