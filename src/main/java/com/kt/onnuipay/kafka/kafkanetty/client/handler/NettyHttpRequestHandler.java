package com.kt.onnuipay.kafka.kafkanetty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/*
 * TODO SMS/APNs/FMS에 따라 규격이 달라진다. 규격에 맞는 핸들러 각각 구현해야 한다 220603 조현일
 * 
 * */


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

@Slf4j
@Setter
public class NettyHttpRequestHandler extends SimpleChannelInboundHandler<Object> {

	private final HttpRequest initRequest;
	private final FullHttpRequest requestWithpayLoad;

	public NettyHttpRequestHandler(HttpRequest initRequest, FullHttpRequest requestWithpayLoad) {
		this.initRequest = initRequest;
		this.requestWithpayLoad = requestWithpayLoad;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
         log.debug("Initial Request to {}",initRequest);
         
         ctx.writeAndFlush(initRequest);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(!(msg instanceof HttpObject)) {
			/*
			 * TODO Handler가 받은 객체가 Http 형식이 아닐 경우 오류 처리하는게 좋을지? 220603 조현일
			 * */
			throw new IllegalArgumentException("Server Only Accepts Http msg");
		}
		
		logResponseEntityFromServer(msg);
		
		  if (msg instanceof HttpContent) {
			  
			  if (msg instanceof LastHttpContent) {
				  
				  requestWithpayLoad.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
				  ctx.writeAndFlush(requestWithpayLoad);
				  ctx.close(); 
				  
					/*
					 * TODO KEEPALIVE 요청 컨트롤 더 상세하게 하기 220603 조현일
					 * */
	            }
			  
			  
	        }
		
		
		
	}

	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		
		if(cause instanceof IllegalArgumentException) {
			log.error("Illegal Argument : Not Http Format",cause);
			ctx.close();

		}
		/*
		 * TODO 
		 * 에러처리
		 * */
	}
	
	private void logResponseEntityFromServer(Object msg) {
		if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            log.info("STATUS: " + response.status());
            log.info("VERSION: " + response.protocolVersion());

        
            
            if (!response.headers().isEmpty()) {
                for (CharSequence name: response.headers().names()) {
                    for (CharSequence value: response.headers().getAll(name)) {
                        System.err.println("HEADER: " + name + " = " + value);
                    }
                }
                System.err.println();
            }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;

            log.info("recived Content : {}",content.content().toString(CharsetUtil.UTF_8));

            if (content instanceof LastHttpContent) {
            	log.info("} END OF CONTENT");
            }
        }
        
		
		}
	}

	
}
