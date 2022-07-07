package com.kafka.kafkanetty.client.test.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * FCM 연동 절차
 * 
 * 1. 구글 인증 절차 진행 후 auth Key 생성
 * 2.FCM 용 메시지 생성
 * 3. FMC 전송 
 * 4. 응답에 따른 추가 절차 진행
 * 
 * 
 * **/

@DisplayName("PUSH를 위한 구글 인증 진행 및 타입, 종류에 따른 메시지 전송 테스트")
public class PushHandlerTest {
	



	
	@AfterEach
	public void cleanUp() {

	}
	
	@Test
	@DisplayName("PushSingleManager에 넣은 사용자가 수신 여부를 N로 할 경우 vailidationMng가 던진 UserNotAllowNotification을 잡지 않고 위로 던진다")
	public void test2() {
		
	

	}
	

	@Test
	@DisplayName("PushSingleManager에 넣은 사용자의 정보가 invalid 할 경우, vailidationMng가 던진 UserInfoInvalidException을 잡지 않고 위로 던진다")
	public void test2_1() {
		
	

	}
	

}
