package com.kafka.kafkanetty;

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
