package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

import com.kafka.kafkanetty.client.handler.manager.ValidationManager;
import com.kafka.kafkanetty.client.handler.manager.impl.ValidationManagerImpl;
import com.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kafka.kafkanetty.exception.UserInfoInvalidException;
import com.kafka.kafkanetty.exception.UserNotAllowNotificationException;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

import util.TestUtil;

@DisplayName("msg 파싱 벨리데이션 매니저 테스트")
public class ValidationManagerTest {

	SmsPushMapper mapper = TestUtil.mapper;
	TempMongodbTemplate mongo = TestUtil.mongo;
	
	ValidationManager validMng = new ValidationManagerImpl(mapper,mongo);
	
	MsgFromKafkaVo vo = TestUtil.voForSinglePush;
	
	@AfterEach
	public void cleanUp() {
		Mockito.reset(mongo);
		Mockito.reset(mapper);
}

	@Test
	@DisplayName("User 정보가 Invalid할 경우, 실패한 User 정보를 몽고DB에 입력한다.")
	public void test1() {
		UserInfoOnPush resultOfQuery= givenCondition(false,true);


		validMng.validSingleUserInfo(vo);
		Mockito.verify(mongo, Mockito.times(1)).insertDbHistory(ArgumentMatchers.any(ResultOfPush.class));
		
	}

	
	@Test
	@DisplayName("User가 PushYn을 n으로 할 경우 실패한 User 정보를 몽고DB에 입력한다.")
	public void test2() {
		
		UserInfoOnPush resultOfQuery = givenCondition(true,false);
		
		validMng.validSingleUserInfo(vo);
		Mockito.verify(mongo, Mockito.times(1)).insertDbHistory(ArgumentMatchers.any(ResultOfPush.class));


	}


	@Test
	@DisplayName("User 정보가 valid하고, PushYn을 Y로 할 경우, return한다")
	public void test3() {
		
		UserInfoOnPush resultOfQuery =givenCondition(true,true);
		
		validMng.validSingleUserInfo(vo);
		Mockito.verify(mongo, Mockito.times(0)).insertDbHistory(ArgumentMatchers.any(ResultOfPush.class));



	}
	
	

	private UserInfoOnPush givenCondition(boolean validation, boolean pushYn) {
		UserInfoOnPush resultOfQuery = Mockito.mock(UserInfoOnPush.class);
		
		Mockito.when(mapper.getIfSendYnByUserNo(vo)).thenReturn(resultOfQuery);
		Mockito.when(resultOfQuery.validation()).thenReturn(validation);
		Mockito.when(resultOfQuery.getPushYn()).thenReturn(pushYn);
		
		return resultOfQuery;
	}


	
}
