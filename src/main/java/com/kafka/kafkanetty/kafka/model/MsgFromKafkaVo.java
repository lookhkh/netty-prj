package com.kafka.kafkanetty.kafka.model;

import lombok.Builder;
import lombok.Data;
/**
 * @author choHyunIl
 * TODO KAFKA MSG 포맷 협의 필요
 * TODO PUSH 서버에서 통신을 위한 메시지 생성 이외에 요청 메시지를 건들여야 하는 부분이 있을지 확인 필요 
 * **/
@Data
public class MsgFromKafkaVo {

	private KafkaKeyEnum key;
	private String payload;
	
	@Builder
	public MsgFromKafkaVo(KafkaKeyEnum key, String payload) {
		this.key = key;
		this.payload = payload;
	}
	
	
}
