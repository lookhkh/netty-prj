package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.exception.FirebaseMessageRunTimeException;
import com.kt.onnuipay.kafka.kafkanetty.exception.FirebaseServerError;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;

import datavo.msg.MessageWrapper;
import datavo.msg.util.MessageUtils;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.netty.internal.shaded.reactor.pool.PoolAcquirePendingLimitException;
import reactor.util.retry.Retry;

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


@Component("push-single-manager")
@Slf4j
public class PushSingleManager extends  CommonPushManager {

	public PushSingleManager(FirebaseMessaging instance, @Qualifier("fcm-client") WebClient client) {
        super(instance,client);
    }

    @Override
    public void processResult(String jsonRequestBody) {
        System.out.println(jsonRequestBody);
        
    }

    @Override
    public String getJsonMsgFromVo(MessageWrapper vo) {
 //{"message":{"notification":{"title":"noti","body":"body"},"token":"token"}} 이렇게 나와야 함.

        
        Map<String, ? super Object> reqBody = new HashMap<>();
        
        if(vo.isUnicast()) {
            reqBody.put("message", vo.getMessageObjList().get(0));
        }else {
            reqBody.put("multicast", vo.getMulticastMessageObjList().get(0));
        }
        
        String result =  MessageUtils.toJson(reqBody, Map.class);
        
        log.info("직렬화 결과 {}",result);
        //{"message":{"notification":{"title":"noti","body":"body"},"token":"token"}} 이렇게 나와야 함.

        
        return result;
    }	
}
