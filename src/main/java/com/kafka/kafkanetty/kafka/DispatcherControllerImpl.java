package com.kafka.kafkanetty.kafka;

import org.springframework.stereotype.Component;

import com.kafka.kafkanetty.client.request.wrapper.ClientRequestWrapper;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class DispatcherControllerImpl implements DispatcherController{

	private final KafkaMsgParser parser;
	

	
	@Override
	public void route(String msg) {
		log.debug("Controller recived msg {}",msg);
		MsgFromKafkaVo vo = parser.parse(msg);
		
		switch(vo.getCodeOfType()) {
			case 0 : {
				System.out.println("싱글 요청");
			}
			case 1 : {
				System.out.println("대량 발송");
			}
		}

	}
}
