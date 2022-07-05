package com.kt.onnuipay.kafka.kafkanetty.client.handler;

import org.asynchttpclient.netty.SimpleChannelFutureListener;
import org.springframework.stereotype.Service;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.Mas;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * 
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


/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 1.
 * @apiNote 모든 hanlder에서 발생하는 ERROR를 잡고, 처리한다.
 */

@Slf4j
@Service(value = "exception_hospital_handler")
public class ExceptionHospitalHandler extends ChannelInboundHandlerAdapter {


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("UnHandled exception happed channelInfo -> {}, cause -> ",ctx.channel(),cause, cause);
		
		Mas mas = Mas
				.builder()
				.method(XMLConstant.REQ_UNREGIST)
				.reason("0")
				.build();
		
		ctx.writeAndFlush(mas).addListener(new SimpleChannelFutureListener() {
			
			@Override
			public void onSuccess(Channel channel) {
				try {
					channel.close().sync();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFailure(Channel channel, Throwable cause) {
				try {
					channel.close().sync();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
	}

}