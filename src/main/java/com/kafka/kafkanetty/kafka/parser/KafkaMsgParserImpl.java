package com.kafka.kafkanetty.kafka.parser;

import org.springframework.stereotype.Component;

import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaMsgParserImpl implements KafkaMsgParser {

	@Override
	public MsgFromKafkaVo parse(String msg) {
		// TODO Auto-generated method stub
		return null;
	}
}
