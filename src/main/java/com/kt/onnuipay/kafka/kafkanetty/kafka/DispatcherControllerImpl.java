package com.kt.onnuipay.kafka.kafkanetty.kafka;

import org.springframework.stereotype.Component;

import com.kt.onnuipay.kafka.kafkanetty.exception.InvalidMsgFormatException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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


@Component
@AllArgsConstructor
@Slf4j
public class DispatcherControllerImpl implements DispatcherController{

	private final KafkaMsgParser parser;
	private final DynamicHandlerManager manager;

	
	@Override
	public ResultOfPush route(String msg) {
		try {
			log.debug("Controller recived msg {}",msg);
			
			MsgFromKafkaVo vo = parser.parse(msg);
			
			ResultOfPush result =  manager.consume(vo);
			
			log.debug("result is => {}",result);
			
			/**
			 * TODO 수동 커밋, 자동 커밋에 따라 추가 로직 필요 220610 조현일
			 * 
			 * **/
			
			return result;
		} catch(InvalidMsgFormatException e) {
			
			log.warn("Message format is not appropriate");
			
			return ResultOfPush.builder()
						.id("")
						.vo(null)
						.success(false)
						.build();
			
		}
		
	

	}
}
