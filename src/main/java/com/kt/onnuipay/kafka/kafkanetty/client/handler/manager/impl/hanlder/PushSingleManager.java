package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import java.io.IOException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Component;

import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.config.FireBaseConfig;

import datavo.msg.MessageWrapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
@AllArgsConstructor
@Component("push-single-manager")
@Slf4j
public class PushSingleManager implements SendManager {
	
	//private final FirebaseMessaging instance;
	private final AsyncHttpClient client;
	private final FireBaseConfig config;
	
	@Override
	public void send(MessageWrapper vo)  {
		log.info("PushSingleSendManager received {}",vo);
		
        try {
            Request r = client.prepareGet("https://jsonplaceholder.typicode.com/todos/1").addHeader("Authorization", "bearer "+config.getAccessToken()).build();
            log.info("{}",r.toString());
            ListenableFuture<Response> result =  client.executeRequest(r);
            
            result.toCompletableFuture().thenAccept(res->log.info("{} {}",res, Thread.currentThread().getName()));

            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
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
