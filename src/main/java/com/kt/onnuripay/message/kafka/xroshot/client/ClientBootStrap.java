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
package com.kt.onnuripay.message.kafka.xroshot.client;


import org.springframework.stereotype.Component;

import com.kt.onnuripay.message.common.exception.RunTimeExceptionWrapper;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.init.SingleHandlerInit;
import com.kt.onnuripay.message.util.LoggerUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/*
 *
 * TODO client BootStrap 설정은
 * 상황에 따라 변경.
 * 
 * TODO 잘못된 HostName, Port로 인하여 커넥션이 실패할 경우 예외처리 해야함.
 * 
 * 
 * */

@Slf4j
@Component
public class ClientBootStrap {

	private final EventLoopGroup workerGroup;

	
	public  ClientBootStrap(EventLoopGroup workerGroup) {
		this.workerGroup = workerGroup;
	
	}
	
	public Channel start(ChannelInitializer<SocketChannel> init, String host, int port)   {
		
		LoggerUtils.logDebug(log, "init netty client init class {}, host : {}, port : {}",init,host,port); 
		
        try {
            Bootstrap b = new Bootstrap(); 
            b.group(this.workerGroup); 
            b.channel(NioSocketChannel.class); 
            b.option(ChannelOption.SO_KEEPALIVE, true);
            
            b.handler(init);

            ChannelFuture f = b.connect(host, port).sync();
                        
            return f.channel();
            
        } catch (InterruptedException e) {
			e.printStackTrace();
			throw new RunTimeExceptionWrapper(e.getMessage(), null, e);
		}
     
		
	}
}
