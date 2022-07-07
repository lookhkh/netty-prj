package com.kt.onnuipay.kafka.kafkanetty.kafka;

import org.springframework.stereotype.Component;

import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;
import com.kt.onnuipay.kafka.kafkanetty.exception.RunTimeExceptionWrapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.dynamic.DynamicHandlerManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

import datavo.msg.MessageWrapper;
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
	private final TempMongodbTemplate db;
	
	@Override
	public void route(String msg) {
		
		
		try {
			log.info("Controller recived msg {}",msg);
			
			MessageWrapper vo = parser.parse(msg);
						
			manager.consume(vo);

		}catch(JsonDataProcessingWrapperException e) {
				log.warn("can`t parsing this recived msg into JSON {}",e.getMessage());

			
				db.insertDbHistory(
						ResultOfPush.builder()
							.vo(null)
							.metaData(null)
							.isSuccess(false)
							.reason(e)
							.build());
	


		}catch(RunTimeExceptionWrapper e) {

				log.error("Unknown Error Happend {}",e.getVo(),e);
				
		
				db.insertDbHistory(
						ResultOfPush.builder()
							.vo((MessageWrapper)e.getVo())
							.metaData(null)
							.isSuccess(false)
							.reason(e)
							.build());

		}
	}
}
