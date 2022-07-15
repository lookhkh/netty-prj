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
package com.kt.onnuripay.kafka.dynamic;

import org.springframework.stereotype.Component;

import com.kt.onnuripay.common.exception.RunTimeExceptionWrapper;

import datavo.msg.MessageWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class DynamicHandlerManager {

	private final DynamicHandlerFactoryMethod factory;
	

	public void consume(MessageWrapper vo) {
		
		log.info("DynamicHanlder recived {}",vo);
				
		try {
					
			 factory
			     .getInstance(vo)
			     .send(vo);
		}

		catch(Exception e) {
			
			log.error("{}, unknown error happend == recivedVo => ",e.getMessage(),vo,e);

			throw new RunTimeExceptionWrapper("unknown error happend",vo,e);

		}
	}

}
