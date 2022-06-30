package com.kt.onnuipay.kafka.kafkanetty.client.handler.async.impl;

import java.util.concurrent.CompletableFuture;

import org.asynchttpclient.Response;
import org.springframework.stereotype.Component;

import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.AbstractAsyncXroshotHandler;
import com.kt.onnuipay.kafka.kafkanetty.exception.XroshotRuntimeException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.SmsPushServerInfoVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ParsingServerResponse extends AbstractAsyncXroshotHandler<String, SmsPushServerInfoVo,SmsPushServerInfoVo > {

	
	public ParsingServerResponse(XMLParser parser) {
		super(parser);
	}
	
	
	@Override
	public CompletableFuture<SmsPushServerInfoVo> execute(String target) {
		
		SmsPushServerInfoVo info = this.deserialzeFromJson(target, SmsPushServerInfoVo.class);
		
		if(!info.valid()) throw new XroshotRuntimeException("xroshot valid error happend "+info.toString(),target);
		
		return CompletableFuture.supplyAsync(()->info);
	}


	



	
}
