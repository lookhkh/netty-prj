package com.kafka.kafkanetty.kafka;

import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DynamicHandlerManager {

	private final SendManager smsSingleMng;
	private final SendManager smsMultipleMng;
	private final SendManager pushSingleSend;
	private final SendManager pushMultipleSend;
	
	public void consume(MsgFromKafkaVo vo) {
		
		
		if(vo.getCodeOfType() ==0 && vo.getTypeValue() == 2) { //단건 SMS
			smsSingleMng.send(vo);
		}
		
		if(vo.getCodeOfType() ==1 && vo.getTypeValue() == 2) { //대량 SMS
			smsMultipleMng.send(vo);
		}
		
		if(vo.getCodeOfType() ==0 && vo.getTypeValue() != 2) { //단건 PUSH
			pushSingleSend.send(vo);
		}
		
		if(vo.getCodeOfType() ==1 && vo.getTypeValue() != 2) { //대량 PUSH
			pushMultipleSend.send(vo);
		}
	
		
		
	}

	

}
