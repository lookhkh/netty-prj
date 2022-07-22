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

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.kt.onnuripay.datavo.msg.MessageWrapper;
import com.kt.onnuripay.message.common.exception.RunTimeExceptionWrapper;
import com.kt.onnuripay.message.kafka.client.handler.manager.SendManager;

import lombok.extern.slf4j.Slf4j;



/**
 * @see https://firebase.google.com/docs/cloud-messaging/send-message#java
 * @see Firebase SDK Batch Request can contains up to 500 registration tokens
 * **/
@Component("push-multiple-manager")
@Slf4j
public class PushMultipleManager implements SendManager {

    private final FirebaseMessaging instance;
    private final ExecutorService service;

    
    
   public PushMultipleManager(FirebaseMessaging instance, @Qualifier("fcm-multi-push") ExecutorService service) {
        this.instance = instance;
        this.service = service;
    }

    @Override
    public void send(MessageWrapper vo) {

       if(log.isDebugEnabled()) log.debug("{} received message {}",this.getClass().getName(), vo);

           this.service.submit(()->{
               
               try {
                   
                       BatchResponse res = vo.isUnicast() ? instance.sendAll(vo.getMessageObjList(), false) :  instance.sendMulticast(vo.getMulticastMessage(), false);

                         if(log.isDebugEnabled()) log.debug("총 메시지 개수 : {}, 성공 메시지 개수 : {}, 실패 메시지 개수 : {}",vo.getMessageObjList().size(), res.getSuccessCount(), res.getFailureCount());
                           res.getResponses()
                               .stream()
                               .forEach(sendRes -> {
                                   
                                   String msgId = sendRes.getMessageId();
                                   
                                   if(sendRes.isSuccessful()) { 
                                       
                                       log.info("{} is successfull ",msgId);
                                       /**
                                        * TODO 성공 로직 추가하기 ( DB 적재 로직 추가 ) 220721 조현일
                                        */
                                   }else {
                                       
                                       log.info("msg send failed, reason => {}",sendRes.getException().getErrorCode());
                                       /**
                                        * TODO 실패 로직 추가하기 ( DB 적재 로직 추가 ) 220721 조현일
                                        */
                                   }
                           });
                   
                } catch (FirebaseMessagingException e) {
                    log.error("Firebase related Error happend errorCode ->> {}",e.getMessagingErrorCode(),e);
                    /**
                     * 
                     * TODO Firebase 통신 실패 시 실패 로직 추가하기.
                     * 
                     */
                    throw new RunTimeExceptionWrapper("Firebase Error Happend "+e.getMessage(), vo, e);
                }
           });

    }

   

}
