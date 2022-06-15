package com.kt.onnuipay.kafka.kafkanetty.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.AllArgsConstructor;


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

@AllArgsConstructor
@Configuration
@PropertySource("classpath:application.properties")
public class ClientConfig {
	
    @Autowired
    Environment env;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	
	@Bean
	public EventLoopGroup getDefaultNioEventLoopGroup() {
		
		int threads = Integer.valueOf(env.getProperty("netty.client.loopgroup.threads"));
		
		return new NioEventLoopGroup(threads);
	}
	
	@Bean
	public Bootstrap getDefaultNettyBootStrapForClient() {
		boolean keepAlive = Boolean.valueOf(env.getProperty("netty.client.options.keepAlive"));

		  
	            Bootstrap b = new Bootstrap(); 
	            b.channel(NioSocketChannel.class); 
	            b.option(ChannelOption.SO_KEEPALIVE, keepAlive);
	            
	            b.handler(new ChannelInitializer<SocketChannel>() {
	                @Override
	                public void initChannel(SocketChannel ch) throws Exception {
	                    ch.pipeline()
	                    	.addLast(new LoggingHandler(LogLevel.DEBUG));
	                }
	            });
	            
	    		return b;

//	            ChannelFuture f = b.connect(this.host, this.port).sync();
//	           
//	            // Wait until the connection is closed.
//	            f.channel().closeFuture().sync();
	        
	}
	
}
