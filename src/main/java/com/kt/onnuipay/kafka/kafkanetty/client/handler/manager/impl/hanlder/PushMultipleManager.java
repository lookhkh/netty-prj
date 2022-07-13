package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.abstractMng.CommonPushManager;

import datavo.msg.MessageWrapper;
import datavo.msg.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
/**
 * @see https://firebase.google.com/docs/cloud-messaging/send-message#java
 * @see Firebase SDK Batch Request can contains up to 500 registration tokens
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


@Component("push-multiple-manager")
@Slf4j
public class PushMultipleManager extends  CommonPushManager {

    public PushMultipleManager(FirebaseMessaging instance, @Qualifier("fcm-client") WebClient client) {
        super(instance, client);

    }
   
    @Override
    public void processResult(String jsonRequestBody) {
        System.out.println(" result "+jsonRequestBody);
    }


    @Override
    public String getJsonMsgFromVo(MessageWrapper vo) {
        //{"message":{"notification":{"title":"noti","body":"body"},"token":"token"}} 이렇게 나와야 함.

        
        Map<String, ? super Object> reqBody = new HashMap<>();
        
        if(vo.isUnicast()) {
            reqBody.put("messagesBundle", vo.getMessageObjList());
        }else {
            reqBody.put("multicastBundle", vo.getMulticastMessageObjList());
        }
        
        String result =  MessageUtils.toJson(reqBody, Map.class);
        
        log.info("직렬화 결과 {}",result);
        //{"message":{"notification":{"title":"noti","body":"body"},"token":"token"}} 이렇게 나와야 함.

        
        return result;
    }   
    

   

}
