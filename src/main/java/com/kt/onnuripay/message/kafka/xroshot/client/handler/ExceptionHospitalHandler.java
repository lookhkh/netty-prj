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

import com.kt.onnuripay.message.kafka.xroshot.client.channelmanager.XroshotChannelManager;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;

import io.grpc.netty.shaded.io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 1.
 * @apiNote 모든 hanlder에서 발생하는 ERROR를 잡고, 처리한다. 처리한 이후에는 로그아웃 메시지를 발송한다.
 */

@Sharable
@Slf4j
@Service(value = "exception_hospital_handler")
@AllArgsConstructor
public class ExceptionHospitalHandler extends ChannelInboundHandlerAdapter {

    private final XroshotChannelManager manager;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	    

    		if(XroshotChannelManager.isLoginSuccess(ctx.channel())) createChannelFuture(ctx);		
    	   
    	    ctx.close()
    	        .sync()
    	        .addListener(retryXroshotConnection(ctx, cause, manager));
    	    
	}


	/**
	 * 
	 * @param ctx
	 * @param cause
	 * @param manager
	 * @return
	 * @apiNote connection이 끊어진 이후, 새로운 커넥션을 맺는다.
	 */
    private GenericFutureListener<Future<? super Void>> retryXroshotConnection(ChannelHandlerContext ctx,
            Throwable cause, XroshotChannelManager manager) {
        return new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                log.error("Channel {} is closed because of {}, and retry connection",ctx.channel(),cause);
                manager.connectToXroshotServer();
                
            }
        };
    }

 

    private void createChannelFuture(ChannelHandlerContext ctx) {    
        
        Mas mas = Mas
                .builder()
                .method(XMLConstant.REQ_UNREGIST)
                .reason("0")
                .build();
        
        ctx.writeAndFlush(mas);

    }


}
