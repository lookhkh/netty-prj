package com.kt.onnuipay.kafka.kafkanetty.kafka;

import org.springframework.stereotype.Component;

import com.kt.onnuipay.kafka.kafkanetty.exception.DataBodyInvalidException;
import com.kt.onnuipay.kafka.kafkanetty.exception.InvalidMsgFormatException;
import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;
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
		
		ResultOfPush result = null;
		
		try {
			log.debug("Controller recived msg {}",msg);
			
			MsgFromKafkaVo vo = parser.parse(msg);
			
			result =  manager.consume(vo);
			
			
			/**
			 * TODO 수동 커밋, 자동 커밋에 따라 추가 로직 필요 220610 조현일
			 * 
			 * **/
			log.info("result is => {}",result);

			return result;
		} catch(DataBodyInvalidException e) {

			log.warn("MessageBody is Invalid",e);

			result= ResultOfPush.builder()
					.id("")
					.vo(e.getErrorVo())
					.success(false)
					.reason(e)
					.build();
			
			return result;

		}catch(JsonDataProcessingWrapperException | InvalidMsgFormatException e) {
			log.warn("can`t parsing msg into JSON ",e.getMessage());
			
			result= ResultOfPush.builder()
					.id("")
					.vo(null)
					.success(false)
					.reason(e)
					.build();
			

			return result;


		}catch(Exception e) {
			log.error("Unknown Error Happend ",e);
			
			result= ResultOfPush.builder()
					.id("")
					.vo(null)
					.success(false)
					.reason(e)
					.build();
			

			return result;
			
		}
	

	}
}
