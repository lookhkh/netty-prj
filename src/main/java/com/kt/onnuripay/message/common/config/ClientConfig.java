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
package com.kt.onnuripay.message.common.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.kt.onnuripay.message.common.config.unit.fcm.FireBaseConfig;
import com.kt.onnuripay.message.common.config.vo.FcmConfigParameter;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.TcpClient;


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
@Configuration
@PropertySource("classpath:application.properties")
@AllArgsConstructor
public class ClientConfig {
	
    /**
     * fcm.eventloop.thread.number = FCM 이벤트 루프 개수 설정 값.
     * fcm.connection.max = FCM 커넥션 수 설정값. 
     * fcm.connection.pool.idleTime.max = FCM 커넥션 풀 내의 최대 IDLE 허용값.
     * fcm.connection.pool.lifeTime.max = 커넥션 풀 내의 최대 생존 기간
     * fcm.connection.pool.evictTime = 커네션 풀 내의 채널 검사 주기
     * fcm.connection.timeout = fcm 내 커넥션 read / write timeout 시간
     * 
     */

    private final FcmConfigParameter parameter;
    
    
    private final FireBaseConfig firebaseConfig;

    
	
	@Bean("fcm-client") 
	public WebClient getClient() {
	    
	        
	    
	       LoopResources loop = LoopResources.create("worker-event-loop", 1, parameter.getEventLoops(), true);
	       
	       DefaultUriBuilderFactory uriFactory = new DefaultUriBuilderFactory(parameter.getBaseUrl());
	       
	       
	       ConnectionProvider provider =
	               ConnectionProvider.builder("custom")
	                                 .maxConnections(parameter.getMaxConnection())
	                                 
	                                 .maxIdleTime(Duration.ofSeconds(parameter.getMaxIdleTime()))           
	                                 .maxLifeTime(Duration.ofSeconds(parameter.getMaxLifeTime()))           
	                                 .pendingAcquireTimeout(Duration.ofSeconds(parameter.getPendingAcquireTimeout())) 
	                                 
	                                 .evictInBackground(Duration.ofSeconds(parameter.getEvictTime()))    
	                                 
	                                 .lifo()
	                                 .build();
	       
	       
	       
	       ReactorResourceFactory factory = new ReactorResourceFactory();
	       factory.setLoopResources(loop);
	       factory.setConnectionProvider(provider);
	       
	       

	       WebClient client = WebClient.builder()
	               .uriBuilderFactory(uriFactory)
	               .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer "+firebaseConfig.getAccessToken())
	               .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
	               .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	               .clientConnector(new ReactorClientHttpConnector(
	                       factory,
	                       httpClient -> httpClient
	                           .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, parameter.getTimeout() * 1000)
	                           .doOnConnected(connection ->
	                               connection.addHandlerLast(new ReadTimeoutHandler(parameter.getTimeout())
	                               ).addHandlerLast(new WriteTimeoutHandler(parameter.getTimeout()))
	                           ).responseTimeout(Duration.ofSeconds(parameter.getTimeout())) 
	                   ))
	               .build();
	       
	       
	       return client;
	}
	
	
	@Bean
	public Bootstrap getDefaultNettyBootStrapForClient() {
		boolean keepAlive = true;

		  
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
