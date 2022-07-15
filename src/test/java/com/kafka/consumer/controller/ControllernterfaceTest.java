package com.kafka.consumer.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DatabindException;
import com.kt.onnuripay.common.exception.JsonDataProcessingWrapperException;
import com.kt.onnuripay.common.exception.RunTimeExceptionWrapper;
import com.kt.onnuripay.kafka.controller.DispatcherController;
import com.kt.onnuripay.kafka.model.ResultOfPush;

import datavo.msg.MessageWrapper;

/*
 * 
 * 1. Kafka로부터 MSG를 POLL한 이후,
 * 2. 메시지를 파싱한다. 메시지 파싱 시, 실패할 경우, 예외를 반환하고, 결과를 저장한다. 
 * DynamicHandlerManager으로 전달한 이후 결과를 바탕으로 kafka에 commit 한다.
 * 
 * */
@DisplayName("controller는 메시지 파싱 및 컨슘을 중개하며, 아래에서 발생하는 에러를 Catch하여 DB에 적재하낟.")
public class ControllernterfaceTest {

	ControllerSetUp setup = new ControllerSetUp();
	
	DispatcherController controller = setup.getController();

	
	@AfterEach
	public void init() throws JsonProcessingException {
		
		setup.reset();

	}
	
	
	@Test
	@DisplayName("파서에서 JsonDataProcessingWrapperException이 터진 경우, 실패처피하며 DB에 이력을 저장한다.")
	public void test3() {
		
		String invalidMsgFromKafka = anyString();
		
		when(this.setup.getMockParser().parse(invalidMsgFromKafka)).thenThrow(JsonDataProcessingWrapperException.class);
		
		controller.route(invalidMsgFromKafka);

		verify(this.setup.getMockDb(), only()).insertDbHistory(any(ResultOfPush.class));
	
	}

	@Test
	@DisplayName("내부에서 처리못한 Exception이 터진 경우,실패처피하여 DB에 이력을 저장한다.")
	public void test3_1() {
		
		String validMsg = "valid"; 
		
		MessageWrapper mockWrapper = any(MessageWrapper.class);
		
		
		when(this.setup.mockParser.parse(validMsg)).thenReturn(mockWrapper);
		
		RunTimeExceptionWrapper exception = new RunTimeExceptionWrapper(validMsg, mockWrapper, new Exception());
		
		doThrow(exception).when(this.setup.mockHanlder).consume(mockWrapper);
		
		controller.route(validMsg);
		
		ResultOfPush failedResult = ResultOfPush.builder()
										.vo((MessageWrapper)exception.getVo())
										.metaData(null)
										.isSuccess(false)
										.reason(exception)
										.build();
		
		verify(this.setup.mockDb, only()).insertDbHistory(failedResult);
	}
	
	

	
	
	@Test
	@DisplayName("ANDROID 단건 MSG 수신 후 push 후 성공한다.")
	public void test1() throws DatabindException, IOException {
		
		String validMsg = "valid";
		
		MessageWrapper mockWrapper = any(MessageWrapper.class);
		
		when(this.setup.mockParser.parse(validMsg)).thenReturn(mockWrapper);
		doNothing().when(this.setup.mockHanlder).consume(mockWrapper);
		
		assertDoesNotThrow(()->controller.route(validMsg));
	}

	
	



	
	
}
