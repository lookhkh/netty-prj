package com.kafka.kafkanetty.kafka;

import org.springframework.stereotype.Component;

import com.kafka.kafkanetty.exception.InvalidMsgFormatException;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
