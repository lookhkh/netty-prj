package com.kafka.kafkanetty.kafka.parser;

import org.springframework.stereotype.Component;

import com.kafka.kafkanetty.client.request.wrapper.ClientRequestWrapper;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.AllArgsConstructor;


/**
 * 
 * TODO MSG 포맷 결정 되면 파서 만들기 220608 조현일
 * 
 * **/

public interface KafkaMsgParser {

	public MsgFromKafkaVo parse(String msg);
}
