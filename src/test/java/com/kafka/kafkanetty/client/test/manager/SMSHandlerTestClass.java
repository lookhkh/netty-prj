package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kafka.kafkanetty.kafka.consumer.controller.TestVos;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.SmsSingleManager;


/**
 * 
 * 크로샷 연동 절차
 * DEFAULT RESTAPI - XML
 * 1. 전송할 서버 위치 확인
 * 2. 서버 시간 요청
 * 3. SP 로그인(인증)요청
 *  3-1. 파일 업로드 경로 정보 요청
 *  3-2. 파일 업로드 서버 정보 요청
 *  3-3. 파일 업로드 및 완료 요청
 * 4. SMS&MMS 전송 요청
 * 5. DB 저장 요청
 * **/
@DisplayName("메시지 타입 및 종류에 따라 적절한 Handler를 생성 및 조합하는 Init 클래스 작성 테스트 - SMS")
public class SMSHandlerTestClass {

	
	SendManager mng = new SmsSingleManager();

	TestVos vo = TestVos.getTestVos();
	
	
	
	
	String host = "localhost";
	int port = 8080;
	
	
	
	@Test
	@DisplayName("1. 전송할 서버 위치 확인")
	public void test() {
		
		
		
		
		assertTrue(false);
		
	}
	

	@Test
	@DisplayName("2. 서버 시간 요청")
	public void test2() {
		
		assertTrue(false);
		
	}
	

	@Test
	@DisplayName("3. SP 로그인(인증)요청")
	public void test3() {
		
		assertTrue(false);
		
	}
	

	@Test
	@DisplayName("3-1. 파일 업로드 경로 정보 요청")
	public void test3_1() {
		
		assertTrue(false);
		
	}
	

	@Test
	@DisplayName("3-2. 파일 업로드 서버 정보 요청")
	public void test3_2() {
		
		assertTrue(false);
		
	}
	
	@Test
	@DisplayName("3-3. 파일 업로드 및 완료 요청")
	public void test3_3() {
		
		assertTrue(false);
		
	}
	
	@Test
	@DisplayName("4. SMS&MMS 전송 요청")
	public void test4() {
		
		assertTrue(false);
		
	}
	
	@Test
	@DisplayName("5. DB 저장 요청")
	public void test5() {
		
		assertTrue(false);
		
	}
	
	
}
