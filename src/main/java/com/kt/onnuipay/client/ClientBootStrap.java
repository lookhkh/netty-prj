package com.kt.onnuipay.client;


import org.springframework.stereotype.Component;

import com.kt.onnuipay.kafka.kafkanetty.exception.RunTimeExceptionWrapper;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
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
@Getter
@Component
public class ClientBootStrap {

	private final EventLoopGroup workerGroup;
	
	public  ClientBootStrap(EventLoopGroup workerGroup) {
	
		this.workerGroup = workerGroup;
	
	}
	


	public Channel start(ChannelInitializer<SocketChannel> init, String host, int port)   {
		
        
        try {
            Bootstrap b = new Bootstrap(); 
            b.group(this.workerGroup); 
            b.channel(NioSocketChannel.class); 
            b.option(ChannelOption.SO_KEEPALIVE, true);
            
            b.handler(init);

            ChannelFuture f = b.connect(host, port).sync();
            return f.channel();
            // Wait until the connection is closed.
            
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RunTimeExceptionWrapper(e.getMessage(), null, e);
		}
     
		
	}
}
