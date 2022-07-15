package com.kafka.kafkanetty.client.test.manager.dynamichanlder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kt.onnuripay.common.exception.RunTimeExceptionWrapper;
import com.kt.onnuripay.kafka.client.handler.manager.SendManager;
import com.kt.onnuripay.kafka.dynamic.DynamicHandlerFactoryMethod;
import com.kt.onnuripay.kafka.dynamic.DynamicHandlerManager;

import datavo.msg.MessageWrapper;

@DisplayName("다이내믹 핸들러 인터페이스 테스트")
public class DynamicHanderInterfaceTest {

	DynamicHander_setup setup = new DynamicHander_setup();
	
	DynamicHandlerFactoryMethod mockFactory = setup.getMockFactory();

	DynamicHandlerManager hanlder = new DynamicHandlerManager(mockFactory);
	
	@AfterEach
	public void reset() {
		this.setup.reset();
	}
	
	@Test
	@DisplayName("Send-Manager 중 하나가 에러를 던질 경우, 해당 에러를 Runtime으로 묵어서 위로 던진다")
	public void test1() {
		SendManager single_push_manager = setup.getPush_single_manager();
		MessageWrapper mockMsg = mock(MessageWrapper.class);
		
		when(mockFactory.getInstance(any(MessageWrapper.class))).thenReturn(single_push_manager);
		
		doThrow(RuntimeException.class).when(single_push_manager).send(any(MessageWrapper.class));
		
		assertThrows(RunTimeExceptionWrapper.class, ()-> this.hanlder.consume(mockMsg));
	}
			
	
}
