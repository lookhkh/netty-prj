package com.kt.onnuipay.kafka.kafkanetty.client.handler.async.exception;

public interface AsyncExceptionHanlder<T,V> {

	public V handlerException(T t); 
}
