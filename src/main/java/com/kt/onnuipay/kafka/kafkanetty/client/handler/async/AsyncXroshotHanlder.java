package com.kt.onnuipay.kafka.kafkanetty.client.handler.async;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface AsyncXroshotHanlder<T,V> {

	public CompletableFuture<V> execute(T target);
	
}
