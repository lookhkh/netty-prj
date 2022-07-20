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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.kt.onnuripay.datavo.msg.MessageWrapper;
import com.kt.onnuripay.datavo.msg.util.MessageUtils;
import com.kt.onnuripay.message.kafka.client.handler.manager.hanlder.abstractMng.CommonPushManager;

import lombok.extern.slf4j.Slf4j;

@Component("push-single-manager")
@Slf4j
public class PushSingleManager extends  CommonPushManager {

	public PushSingleManager(@Qualifier("fcm-client") WebClient client) {
        super(client);
    }

    @Override
    public void processResult(String jsonRequestBody) {
        log.info(" PushSingleManager Process result "+jsonRequestBody);
        
    }

    @Override
    public String getJsonMsgFromVo(MessageWrapper vo) {

        Map<String, ? super Object> reqBody = new HashMap<>();
        
        
        reqBody.put("message", vo.getMessageObjList().get(0));
        reqBody.put("validate_only", true);
        
        
        String result =  MessageUtils.toJson(reqBody, Map.class);
        
        if(log.isDebugEnabled())log.debug("직렬화 결과 {}",result);
        //{"message":{"notification":{"title":"noti","body":"body"},"token":"token"}} 이렇게 나와야 함.
        
        return result;
    }	
}
