package com.kt.onnuipay.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
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
//@Component("client-bootstrap")
public class ClientBootStrap {
	
	

	private final String host;
	private final int port;
	private EventLoopGroup workerGroup;
	
	public  ClientBootStrap(String host, int port, EventLoopGroup workerGroup) {
		this.host = host;
		this.port = port;
		
		if(workerGroup == null) {
			this.workerGroup = new NioEventLoopGroup();
		}else {
	        this.workerGroup = workerGroup; //공유자원,bootstrap 설정 시 사용
		}

	}
	

	public  ClientBootStrap(String host, int port) {
		this.host = host;
		this.port = port;
		
		if(workerGroup == null) {
			this.workerGroup = new NioEventLoopGroup();
		}

	}
	
	
	

	public boolean start()  {
		
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //공유자원,bootstrap 설정 시 사용
        
        try {
            Bootstrap b = new Bootstrap(); 
            b.group(workerGroup); 
            b.channel(NioSocketChannel.class); 
            b.option(ChannelOption.SO_KEEPALIVE, true);
            
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                    	.addLast(new LoggingHandler(LogLevel.DEBUG));
                }
            });

            ChannelFuture f = b.connect(this.host, this.port).sync();
           
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
            workerGroup.shutdownGracefully();
        }
		
		return true;
	}

	public void stop() {
        workerGroup.shutdownGracefully();
	}
}