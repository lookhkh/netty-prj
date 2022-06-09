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
    private TypeOfSending type;
	private Object payload;
	
	@Builder
	public MsgFromKafkaVo(KafkaKeyEnum key, TypeOfSending type, Object payload) {
		this.key = key;
		this.type = type;
		this.payload = payload;
	}

	/**
	 * 
	 * @return 0 : SINGLE / 1 : MULTIPLE
	 * 
	 * **/
	public int getCodeOfType() {
		return this.type.getCode();	
	}

	/**
	 * @return 0 : android | 1 : IOS | 2 : SMS 
	 * 
	 * **/
	public int getTypeValue() {
		// TODO Auto-generated method stub
		return this.key.getTypeCode();
	}
	
	
	
	
}
