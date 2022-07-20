package com.kafka.kafkanetty.client.test.manager.dynamichanlder;

import static org.mockito.Mockito.mock;

import com.kt.onnuripay.message.kafka.client.handler.manager.SendManager;
import com.kt.onnuripay.message.kafka.client.handler.manager.hanlder.impl.PushMultipleManager;
import com.kt.onnuripay.message.kafka.client.handler.manager.hanlder.impl.PushSingleManager;
import com.kt.onnuripay.message.kafka.client.handler.manager.hanlder.impl.SmsMultipleManager;
import com.kt.onnuripay.message.kafka.client.handler.manager.hanlder.impl.SmsSingleManager;
import com.kt.onnuripay.message.kafka.dynamic.DynamicHandlerFactoryMethod;
import com.kt.onnuripay.message.kafka.dynamic.DynamicHandlerManager;

import lombok.Data;

@Data
public class DynamicHander_setup  {

	SendManager sms_single_manager = mock(SmsSingleManager.class);
	SendManager sms_multiple_manager = mock(SmsMultipleManager.class);
	SendManager push_single_manager = mock(PushSingleManager.class);
	SendManager push_multiple_manager = mock(PushMultipleManager.class);
	
	DynamicHandlerFactoryMethod mockFactory = mock(DynamicHandlerFactoryMethod.class);

	DynamicHandlerManager hanlder = new DynamicHandlerManager(mockFactory);
			 
	public void reset() {
		this.sms_single_manager = mock(SmsSingleManager.class);
		this.sms_multiple_manager = mock(SmsMultipleManager.class);
		this.push_single_manager = mock(PushSingleManager.class);
		this. push_multiple_manager = mock(PushMultipleManager.class);
		
		this. mockFactory = mock(DynamicHandlerFactoryMethod.class);

		
	}
	
	
	
}