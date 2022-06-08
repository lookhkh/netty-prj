package com.kafka.kafkanetty.kafka;

import org.springframework.stereotype.Component;

import com.kafka.kafkanetty.client.request.wrapper.ClientRequestWrapper;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@Slf4j
@Component
public class DispatcherController {

	private final KafkaMsgParser msgParser;
	
	public void route(String msg) {
		log.info("Recived msg : {}",msg);
		ClientRequestWrapper<MsgFromKafkaVo> s =  msgParser.parse(msg);
		
	}

}
