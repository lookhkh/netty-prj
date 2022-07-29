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


import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.common.exception.RunTimeExceptionWrapper;
import com.kt.onnuripay.message.kafka.xroshot.client.channelmanager.XroshotChannelManager;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.util.NewXroshotAuth;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.auth.AuthInfoVo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.serverTime.ServerTimeVo;
import com.kt.onnuripay.message.util.LoggerUtils;

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

@Slf4j
@AllArgsConstructor
public class RequestAuthTicketHandler extends ChannelInboundHandlerAdapter {
	
	private final XroshotParameter param;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	   LoggerUtils.logDebug(log, "received Msg from previous handler {}", msg);
	          
	    if(msg instanceof ServerTimeVo) {
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
    		LoggerUtils.logDebug(log, "completed xml req -> {}", req.toString());
    					
    		ctx.writeAndFlush(req);
    		
	    }else if(msg instanceof AuthInfoVo) {
	        
	        
	        LoggerUtils.logDebug(log, "received AuhInfo FROM Server {}", msg);	       
	        ctx.channel().attr(XroshotChannelManager.KEY).set(XroshotChannelManager.REQ_AUTH);
	        ctx.pipeline().remove(this);
	        ctx.fireChannelActive(); // session 생성에 성공할 경우, pingRequestHanlder를 실행시킨다.

	    }
		
		
	}
	
	private String createAuthTicket(ServerTimeVo vo, XroshotParameter param) {


		String tempPublicKey =param.getCertFile().substring(param.getIndexBegin(), param.getIndexEnd()) ;
		String tempAuthTicket = param.getServiceProviderId() + "|" + param.getServiceProviderPw() + "|" + param.getEndUserId() + "|" + vo.getTime() + "|" + param.getOneTimeSecretKey();

		try {
			
		    return NewXroshotAuth.encrypto2(tempAuthTicket.getBytes(), tempPublicKey.getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RunTimeExceptionWrapper("Error Happend during generating authticket", vo, e);
		}
	}
}
