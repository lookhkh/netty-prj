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

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.kt.onnuripay.datavo.msg.MessageWrapper;
import com.kt.onnuripay.datavo.msg.util.MessageUtils;
import com.kt.onnuripay.message.common.exception.FirebaseMessageRunTimeException;
import com.kt.onnuripay.message.common.exception.FirebaseServerError;
import com.kt.onnuripay.message.kafka.client.handler.manager.SendManager;

import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.internal.shaded.reactor.pool.PoolAcquirePendingLimitException;
import reactor.util.retry.Retry;

@Component("push-single-manager")
@Slf4j
public class PushSingleManager implements SendManager {
    
    private final WebClient client;
    private final Environment env;

	public PushSingleManager(@Qualifier("fcm-client") WebClient client,Environment env) {
	    this.client = client;
	    this.env =env;
    }

	@Override
	public void send(MessageWrapper vo) {
	    if(log.isDebugEnabled()) log.debug("Manager get input {}",vo);
        
        client.post()
        .body(BodyInserters.fromValue(getJsonMsgFromVo(vo)))         
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError , res -> res.createException())
        .onStatus(HttpStatus::is5xxServerError, res -> Mono.error(new FirebaseServerError(res)))
        .bodyToFlux(String.class)
        .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)).jitter(0.75)
                .filter(throwable -> throwable instanceof FirebaseServerError | throwable instanceof PoolAcquirePendingLimitException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal)->{
                    throw new FirebaseMessageRunTimeException("Retry failed");   
                }))
        .onErrorResume(e->{
            
            if(e instanceof WebClientResponseException) {
                WebClientResponseException t =  (WebClientResponseException)e;
                log.error("Client Logic error happend {}",t.getResponseBodyAsString(CharsetUtil.UTF_8));
            }else {
                log.error("Logic related Error happend {}",e.getMessage());
            }
            
            return Flux.just("에러가 발생했어요  "+e.getMessage());
        })
        .subscribe(str->processResult(str));	    
	}
	
    
    private void processResult(String jsonRequestBody) {
        log.info(" PushSingleManager Process result "+jsonRequestBody);
        /**
         * TODO 처리 로직 추가 예정 220721 조현일
         */
    }

    
    private String getJsonMsgFromVo(MessageWrapper vo) {

        Map<String, ? super Object> reqBody = new HashMap<>();
        
        
        reqBody.put("message", vo.getMessageObjList().get(0));
        reqBody.put("validate_only", env.getProperty("spring.profiles.active").equals("local")? true: false);
        
        String result =  MessageUtils.toJson(reqBody, Map.class);
        
        if(log.isDebugEnabled())log.debug("직렬화 결과 {}",result);
        //{"message":{"notification":{"title":"noti","body":"body"},"token":"token"}} 이렇게 나와야 함.
        
        return result;
    }	
}
