package com.kt.onnuipay.kafka.kafkanetty.kafka;

import org.springframework.stereotype.Component;

import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;
import com.kt.onnuipay.kafka.kafkanetty.exception.RunTimeExceptionWrapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;
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
	private final TempMongodbTemplate db;
	
	@SuppressWarnings("finally")
	@Override
	public ResultOfPush route(String msg) {
		
		ResultOfPush result = null;
		
		try {
			log.info("Controller recived msg {}",msg);
			
			MsgFromKafkaVo vo = parser.parse(msg);
			
			result =  manager.consume(vo);
			
			/**
			 * TODO 수동 커밋, 자동 커밋에 따라 추가 로직 필요 220610 조현일
			 * ㄴ-> 굳이 필요 없을듯? 실패했다고 커밋을 안 하게 되면, 진행이 안됨...ㅠ
			 * ㄴ-> 이보다는, 결과를 모니터링하여 메시지 전송이 실패했을 경우, 확인할 수 있는 방안을 수립하는 것이 효율적일듯
			 * 220620 조현일
			 * **/

		}catch(JsonDataProcessingWrapperException e) {
			log.warn("can`t parsing this recived msg into JSON {}",e.getMessage());
			
			result= ResultOfPush.builder()
					.id("")
					.vo(null)
					.success(false)
					.reason(e)
					.build();


		}catch(RunTimeExceptionWrapper e) {

				log.error("Unknown Error Happend {}",((RunTimeExceptionWrapper) e).getVo(),e);
				
				result= ResultOfPush.builder()
						.id("")
						.vo(((RunTimeExceptionWrapper)e).getVo())
						.success(false)
						.reason(e)
						.build();

		}
		finally {
			log.info("result is => {}",result);
			db.insertDbHistory(result);
			
			return result;
		}
	

	}
}
