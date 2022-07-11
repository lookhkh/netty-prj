package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.config.FireBaseConfig;
import com.kt.onnuipay.kafka.kafkanetty.exception.FirebaseMessageRunTimeException;
import com.kt.onnuipay.kafka.kafkanetty.exception.FirebaseServerError;
import com.kt.onnuipay.kafka.kafkanetty.exception.RunTimeExceptionWrapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;

import datavo.msg.MessageWrapper;
import datavo.msg.util.MessageUtils;
import io.netty.util.CharsetUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
		        
        //{"message":{"notification":{"title":"noti","body":"body"},"token":"token"}} 이렇게 나와야 함.
        
        Map<String, Message> reqBody = new HashMap<>();
        reqBody.put("message", vo.getMessageObjList().get(0));
        
        String result =  MessageUtils.toJson(reqBody, Map.class);
        
        log.info("직렬화 결과 {}",result);

        
        client.post()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(result))         
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError , res -> res.createException())
            .onStatus(HttpStatus::is5xxServerError, res -> res.createException())
            .bodyToFlux(String.class)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
                    .filter(throwable -> throwable instanceof FirebaseServerError)
                    .filter(throwable -> throwable instanceof PoolAcquirePendingLimitException)
                    .filter(throwable -> throwable instanceof Throwable)
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
                
                return Flux.just("에러가 발생했어요 ㅠ, "+e.getMessage());
            })
            .subscribe(str->{
                ResultOfPush p = null;
                
                if(str.contains("에러가 발생")) {
                     p = ResultOfPush.builder()
                                     .isSuccess(false)
                                     .metaData(vo.getMetaData_())
                                     .vo(vo).build();

                }else {
                     p = ResultOfPush.builder()
                                     .isSuccess(true)
                                     .metaData(vo.getMetaData_())
                                     .vo(vo).build();
                }
                
                System.out.println(str+" "+p.toString());
            });
		    
        
     
        
		
//		try {
//		if(vo.isUnicast()) {
//		    log.info("Unicast Single message");
//		    String result = instance.sendAsync(vo.getMessageObjList().get(0)).get(10, TimeUnit.SECONDS);
//            log.info("Unicast Single message => {}",result);
//		}else{
//	          log.info("Multicast Single message");
//		    BatchResponse response =  instance.sendMulticast(vo.getMulticastMessageObjList().get(0));
//		    response.getResponses().forEach(res->log.info("id : {}, result : {}, exception : {}",res.getMessageId(),res.isSuccessful(),res.getException()));
//		    
//		}
//		}catch(FirebaseMessagingException e) {
//		    e.printStackTrace();
//	} catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
		
		
		}	
}
