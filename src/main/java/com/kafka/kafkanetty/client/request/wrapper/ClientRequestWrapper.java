package com.kafka.kafkanetty.client.request.wrapper;

import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientRequestWrapper<T> {

	
	private final T msg;

	public int getMsgType() {
		
		
		if(msg instanceof MsgFromKafkaVo) {
			return ((MsgFromKafkaVo) msg).getCodeOfType();
		}
		
		throw new IllegalArgumentException("Invalid Field Member , type of the Member should be MsgFromKafkaVo -> current class "+msg.getClass());
		
	}
	
	
}
