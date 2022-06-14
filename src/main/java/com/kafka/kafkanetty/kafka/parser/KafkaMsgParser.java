package com.kafka.kafkanetty.kafka.parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DatabindException;
import com.kafka.kafkanetty.exception.InvalidMsgFormatException;
import com.kafka.kafkanetty.kafka.model.DataBody;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;


/**
 * 
 * TODO MSG 포맷 결정 되면 파서 만들기 220608 조현일
 * 
 * **/

public interface KafkaMsgParser {


	public MsgFromKafkaVo parse(String msg);
}
