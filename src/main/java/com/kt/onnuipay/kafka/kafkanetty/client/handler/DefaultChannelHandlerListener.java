package com.kt.onnuipay.kafka.kafkanetty.client.handler;
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

import org.asynchttpclient.netty.SimpleChannelFutureListener;

import com.kt.onnuipay.kafka.kafkanetty.exception.ChannelHandlerExceptionError;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandler;
import lombok.AllArgsConstructor;

/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 1.
 * @apiNote 핸들러가 분리될 때, 간단한 로그를 남기는 리스너
 */

@AllArgsConstructor
public class DefaultChannelHandlerListener extends SimpleChannelFutureListener {

	private final ChannelInboundHandler hanldler;
	
	@Override
	public void onFailure(Channel channel, Throwable cause) {
		cause.fillInStackTrace();
		channel.close();
		throw new ChannelHandlerExceptionError(channel.toString()+" error happend during "+hanldler.getClass().toString(),cause);
		
	}
	
	@Override
	public void onSuccess(Channel channel) {
		channel.pipeline().remove(hanldler);
		
	}
}