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
package com.kt.onnuripay.kafka.client.handler.manager.hanlder.abstractMng;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.kt.onnuripay.common.exception.FirebaseMessageRunTimeException;
import com.kt.onnuripay.common.exception.FirebaseServerError;
import com.kt.onnuripay.kafka.client.handler.manager.SendManager;

import datavo.msg.MessageWrapper;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.netty.internal.shaded.reactor.pool.PoolAcquirePendingLimitException;
import reactor.util.retry.Retry;

@AllArgsConstructor
@Slf4j
public abstract class CommonPushManager implements SendManager{

    private final WebClient client;
    
    /**
     * 
     */
    @Override
    public void send(MessageWrapper vo) {
        
        if(log.isDebugEnabled()) log.debug("Manager get input {}",vo);
        
        client.post()
        .body(BodyInserters.fromValue(getJsonMsgFromVo(vo)))         
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError , res -> res.createException())
        .onStatus(HttpStatus::is5xxServerError, res -> res.createException())
        .bodyToFlux(String.class)
        .retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
                .filter(throwable -> throwable instanceof FirebaseServerError | throwable instanceof PoolAcquirePendingLimitException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal)->{
                    throw new FirebaseMessageRunTimeException("Retry failed");   
                }))
        
        .onErrorResume(e->{
            
            if(e instanceof WebClientResponseException) {
                WebClientResponseException t =  (WebClientResponseException)e;
                log.error("{}",t.getResponseBodyAsString(CharsetUtil.UTF_8));
            }else {
                log.error("Logic related Error happend {}",e.getMessage());
            }
            
            return Flux.just("에러가 발생했어요  "+e.getMessage());
        })
        .subscribe(str->processResult(str));
        
    }

    /**
     * 
     * @param str
     * @apiNote 최종 Flux를 처리하는 로직을 하위 객체에서 Override 후 사용한다. 
     */
    public abstract void processResult(String jsonRequestBody);

    /**
     * 
     * @param vo
     * @apiNote MessageWrapper 객체에서 필요한 정보를 가져오는 로직을 하위 객체에서 override 한다.
     */
    public abstract String getJsonMsgFromVo(MessageWrapper vo);
}
