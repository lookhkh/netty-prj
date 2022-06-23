package com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.api.core.ApiFutureToListenableFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.kt.onnuipay.callback.FutureCallBackWrapper;
import com.kt.onnuipay.client.handler.manager.abstracts.PushManagerAbstract;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

import datavo.msg.MessageWrapper;

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
@Profile(value= {"default", "local","prod"})
@Component
public class SendPushManagerImpl extends PushManagerAbstract {

	@Autowired
	private final TempMongodbTemplate db;
	private final ExecutorService singleExecutorService;
	
	
	

	public SendPushManagerImpl(TempMongodbTemplate db, @Qualifier("single") ExecutorService singleExecutorService) {
		super();
		this.db = db;
		this.singleExecutorService = singleExecutorService;
	}

	@Override
	public void sendPushForSingle(FirebaseMessaging instance, MessageWrapper vo) {
		
		
		ListenableFuture<String> f =  new ApiFutureToListenableFuture<String>(
					instance.sendAsync((Message)vo.getMessage()));
		
		Futures.addCallback(
				f,new FutureCallBackWrapper<String>(vo,db), singleExecutorService);
		
	}
	
	@Override
	protected void sendPushForMulti(FirebaseMessaging instance, MessageWrapper vo) {

		if(vo.isUnicast()) {
			
			
			
		}else {
			
			
			
		}

		/**
		 * TODO MULTI
		 * **/
	}

	
		

	
}
