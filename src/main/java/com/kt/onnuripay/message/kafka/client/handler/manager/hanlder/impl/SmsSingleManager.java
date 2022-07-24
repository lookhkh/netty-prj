/*
 * KT OnnuriPay version 1.0
 *
 *  Copyright ⓒ 2022 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */
package com.kt.onnuripay.message.kafka.client.handler.manager.hanlder.impl;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.kt.onnuripay.datavo.msg.MessageWrapper;
import com.kt.onnuripay.message.kafka.client.handler.manager.SendManager;
import com.kt.onnuripay.message.kafka.xroshot.client.channelmanager.XroshotChannelManager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("sms-single-manager")
@AllArgsConstructor
@Builder
public class SmsSingleManager implements SendManager {

	private final XroshotChannelManager manager;
    
	@Override
	public void send(MessageWrapper vo) {
		log.info("SMS single Msg {}",vo);
		
		manager.send(vo, handleResult());
		
	/*
	 * TODO 크로샷 계약 완료 후, 마무리 지을 예정. 220715 조현일	
	 */
		
			
	}

    private Consumer<MessageWrapper> handleResult() {
        return msg -> log.info("메시지를 전송완료 {}",msg);
    }

	

}
