package com.kafka.kafkanetty.client.test.manager.dynamichanlder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kt.onnuripay.message.kafka.dynamic.DynamicHandlerManager;

/**
 * 1. MSG 타입 및 종류(안드로이드? IOS? SMS? / 단건 발송? 멀티발송?)에 따라, 해당하는 핸들러로 메시지를 라우팅한다.
 * 2. 하위 개체들이 핸들링하지 못한 에러가 발생한 경우, 에러를 RuntimeException으로 Wrapping한 이후, dispatchController에서 처리하도록 한다.
 * 3. 결과를 DispatchController로 반환한다.
 * 
 * 
 * **/

@DisplayName("MSG 타입(SMS, PUSH), 종류(대량, 단건)에 따라 목적으로 하는 객체가 Invoked 하는지")
public class DynamicHandlerMngTest {
	

	
	DynamicHandlerManager manager;
	

	


	@BeforeEach
	public void cleanUp() {

		
	}
	
	@Test
	@DisplayName("SMS 대량 발송 테스트")
	public void test() {
	}

	@Test
	@DisplayName("SMS 단건 발송 테스트")
	public void test1() {
	

	}
	
	@Test
	@DisplayName("Android PUSH 대량 발송 테스트")
	public void test2() {
	


	}

	@Test
	@DisplayName("Android PUSH 단건 발송 테스트")
	public void test3() {
		

	}
	
	@Test
	@DisplayName("dynamicmanager는 senderManager이 반환한 result를 그대로 반환한다.")
	public void test4() {
		
	
		
	}
	
	@Test
	@DisplayName("dynamicmanager는 senderManager에서 에러가 발생할 시, 에러 정보를 담은 fail Result를 반환한다.")
	public void test4_1() {
		
	
	}
	
	@Test
	@DisplayName("dynamicmanager는 senderManager에서 에러가 발생했는데, 해당 객체에서 핸들링하지 못한 경우, 에러를 RunTimeExceptionWrapper로 감싸서 위로 던진다")
	public void test4_2() {
		
		
	}
	

}
