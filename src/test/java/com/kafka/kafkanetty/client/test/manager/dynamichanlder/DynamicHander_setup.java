package com.kafka.kafkanetty.client.test.manager.dynamichanlder;

import static org.mockito.Mockito.mock;

import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.PushMultipleManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.PushSingleManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.SmsMultipleManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.SmsSingleManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.dynamic.DynamicHandlerFactoryMethod;
import com.kt.onnuipay.kafka.kafkanetty.kafka.dynamic.DynamicHandlerManager;

import lombok.Data;
import util.TestSeUpAbstract;

@Data
public class DynamicHander_setup extends TestSeUpAbstract {

	SendManager sms_single_manager = mock(SmsSingleManager.class);
	SendManager sms_multiple_manager = mock(SmsMultipleManager.class);
	SendManager push_single_manager = mock(PushSingleManager.class);
	SendManager push_multiple_manager = mock(PushMultipleManager.class);
	
	DynamicHandlerFactoryMethod mockFactory = mock(DynamicHandlerFactoryMethod.class);

	DynamicHandlerManager hanlder = new DynamicHandlerManager(mockFactory);
			
	@Override
	public void reset() {
		this.sms_single_manager = mock(SmsSingleManager.class);
		this.sms_multiple_manager = mock(SmsMultipleManager.class);
		this.push_single_manager = mock(PushSingleManager.class);
		this. push_multiple_manager = mock(PushMultipleManager.class);
		
		this. mockFactory = mock(DynamicHandlerFactoryMethod.class);

		
	}
	
	
	
}