package com.kt.onnuipay.kafka.kafkanetty.client.handler.async.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.kt.onnuipay.client.ClientBootStrap;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.init.SingleHandlerInit;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.ResourceInfo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.SmsPushServerInfoVo;

@Component
public class PrepareAndStartNettyClient  {

	private final ClientBootStrap boot;
	private final SingleHandlerInit singleChannelInit;
	
	public PrepareAndStartNettyClient(
			ClientBootStrap boot, 
			SingleHandlerInit singleChannelInit) {
		
		this.boot = boot;
		this.singleChannelInit = singleChannelInit;
	
	
	}

	public CompletableFuture<Void> execute(CompletableFuture<SmsPushServerInfoVo> target) {

		 target
			.thenApply(vo -> vo.getResource())
			.thenAccept(this::startTransaction);
		 
		 return null;
		
	}
	
	private void startTransaction(ResourceInfo resource) {
		

		 boot.start(
				 		singleChannelInit.getChannelInit(null),
						resource.getAddress(), 
						resource.getPort());
				
	}

}
