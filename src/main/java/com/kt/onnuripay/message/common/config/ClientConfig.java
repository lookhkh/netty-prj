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

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
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


@Configuration
@PropertySource("classpath:application.properties")
public class ClientConfig {
	
    @Autowired
    Environment env;
    
    @Autowired
    FireBaseConfig firebaseConfig;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean("fcm-client") 
	public WebClient getClient() {
	        LoopResources loop = LoopResources.create("worker-event-loop", 1, 10, true);
	        
	       String baseUrl = env.getProperty("project.properties.fms.fcmUrl");
	       
	       DefaultUriBuilderFactory uriFactory = new DefaultUriBuilderFactory(baseUrl);
	       
	       
	       ConnectionProvider provider =
	               ConnectionProvider.builder("custom")
	                                 .maxConnections(100)
	                                 
	                                 .maxIdleTime(Duration.ofSeconds(20))           
	                                 .maxLifeTime(Duration.ofSeconds(120))           
	                                 .pendingAcquireTimeout(Duration.ofSeconds(60)) 
	                                 
	                                 .evictInBackground(Duration.ofSeconds(120))    
	                                 
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
	                           .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
	                           .doOnConnected(connection ->
	                               connection.addHandlerLast(new ReadTimeoutHandler(5)
	                               ).addHandlerLast(new WriteTimeoutHandler(5))
	                           ).responseTimeout(Duration.ofSeconds(5)) // 0.9.11 부터 추가
	                   ))
	               .build();
	       
	       
	       return client;
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
