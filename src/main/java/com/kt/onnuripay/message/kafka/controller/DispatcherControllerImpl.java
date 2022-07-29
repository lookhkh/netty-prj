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
package com.kt.onnuripay.message.kafka.controller;

import org.springframework.stereotype.Component;

import com.kt.onnuripay.datavo.MetaData;
import com.kt.onnuripay.datavo.msg.MessageWrapper;
import com.kt.onnuripay.message.common.exception.JsonDataProcessingWrapperException;
import com.kt.onnuripay.message.common.exception.RunTimeExceptionWrapper;
import com.kt.onnuripay.message.kafka.client.handler.mapper.PushMapperContainer;
import com.kt.onnuripay.message.kafka.client.handler.mapper.SmsPushMapper;
import com.kt.onnuripay.message.kafka.dynamic.DynamicHandlerManager;
import com.kt.onnuripay.message.kafka.model.ResultOfPush;
import com.kt.onnuripay.message.kafka.parser.KafkaMsgParser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@AllArgsConstructor
@Slf4j
public class DispatcherControllerImpl implements DispatcherController{

	private final KafkaMsgParser parser;
	private final DynamicHandlerManager manager;
	private final PushMapperContainer container;
	
	@Override
	public void route(String msg) {
		
		
		try {
			log.info("Controller recived msg {}",msg);
			
			MessageWrapper vo = parser.parse(msg);
						
			manager.consume(vo);

		}catch(JsonDataProcessingWrapperException e) {
				log.warn("can`t parsing this recived msg into JSON {}",e.getMessage());

			
				container.insetResultOfPush(
						ResultOfPush.builder()
							.vo(null)
							.metaData(null)
							.isSuccess(false)
							.reason(e)
							.build());
	


		}catch(RunTimeExceptionWrapper e) {

				log.error("Unknown Error Happend {}",e.getVo(),e);
				
		
				container.insetResultOfPush(
						ResultOfPush.builder()
							.vo((MessageWrapper)e.getVo())
							.metaData(e.getVo() instanceof MessageWrapper ? (MetaData)e.getVo():null)
							.isSuccess(false)
							.reason(e)
							.build());

		}
	}
}
