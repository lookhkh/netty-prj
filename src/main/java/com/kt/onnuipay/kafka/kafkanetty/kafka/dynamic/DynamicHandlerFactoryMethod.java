package com.kt.onnuipay.kafka.kafkanetty.kafka.dynamic;

import com.kt.onnuipay.client.handler.manager.SendManager;

import datavo.msg.MessageWrapper;

public interface DynamicHandlerFactoryMethod {

	public SendManager getInstance(MessageWrapper vo);
}
