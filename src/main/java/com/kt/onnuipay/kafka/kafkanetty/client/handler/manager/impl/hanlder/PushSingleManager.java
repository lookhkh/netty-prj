package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.config.FireBaseConfig;

import datavo.msg.MessageWrapper;
import datavo.msg.util.MessageUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 
 * TODO 단건 발송 요청 유저의 알림 수신 여부 체크 로직 추가 필요 220609 조현일
 * 
 * **/


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

@Setter
@Component("push-single-manager")
@Slf4j
public class PushSingleManager implements SendManager {
	
	private final FirebaseMessaging instance;
	private final WebClient client;
	
	
	public PushSingleManager(FirebaseMessaging instance, @Qualifier("fcm-client") WebClient client) {
        super();
        this.instance = instance;
        this.client = client;
    }



    @Override
	public void send(MessageWrapper vo)  {
		log.info("PushSingleSendManager received {}",vo);
		
		
		String r = MessageUtils.toJson(vo.getMessageObjList().get(0), Message.class);
        log.info("직렬화 결과 {}",r);
        
        //{"message":{"notification":{"title":"noti","body":"body"},"token":"token"}} 이렇게 나와야 함.
        
        client.post()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(r))
            .retrieve()
            .bodyToFlux(String.class)
            .subscribe(str->System.out.println(str));
		
        
     
        
		
//		try {
//		if(vo.isUnicast()) {
//		    log.info("Unicast Single message");
//		    String result = instance.send(vo.getMessageObjList().get(0));
//         
//		}else{
//	          log.info("Multicast Single message");
//		    BatchResponse response =  instance.sendMulticast(vo.getMulticastMessageObjList().get(0));
//		    log.info("batch response => {}",response.toString());
//		    
//		}
//		}catch(FirebaseMessagingException e) {
//		    e.printStackTrace();
//	}
		
		
		}	
}
