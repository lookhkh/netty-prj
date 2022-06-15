package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.kt.onnuipay.client.handler.manager.ValidationManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.ValidationManagerImpl;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kt.onnuipay.kafka.kafkanetty.exception.UserInfoInvalidException;
import com.kt.onnuipay.kafka.kafkanetty.exception.UserNotAllowNotificationException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.push.MobileAbstractVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

import util.MsgFromKafkaAndroid;
import util.TestUtil;

@DisplayName("msg 파싱 벨리데이션 매니저 테스트")
public class ValidationManagerTest {

	SmsPushMapper mapper = TestUtil.mapper;
	TempMongodbTemplate mongo = TestUtil.mongo;
	
	ValidationManager validMng = new ValidationManagerImpl(mapper,mongo);
	
	MsgFromKafkaVo voOfAndroidPushWithSingle = MsgFromKafkaAndroid.voForSinglePushWithValidDataBody;
	
	@AfterEach
	public void cleanUp() {
		Mockito.reset(mongo);
		Mockito.reset(mapper);
	}

	@Test
	@DisplayName("User 정보가 Invalid할 경우, 실패한 User 정보를 몽고DB에 입력한다.")
	public void test1() {
		UserInfoOnPush resultOfQuery= givenCondition(false,true);

		assertThrows(UserInfoInvalidException.class, ()->validMng.validSingleUserInfo(voOfAndroidPushWithSingle.getTarget().get(0)));

		seeIfMapperInvokedAndInsertDbForLoadingHistory(voOfAndroidPushWithSingle);
		
	}


	
	@Test
	@DisplayName("User가 PushYn을 n으로 할 경우 실패한 User 정보를 몽고DB에 입력한다.")
	public void test2() {
		
		UserInfoOnPush resultOfQuery = givenCondition(true,false);
		
		assertThrows(UserNotAllowNotificationException.class, ()->validMng.validSingleUserInfo(voOfAndroidPushWithSingle.getTarget().get(0)));




	}


	@Test
	@DisplayName("User 정보가 valid하고, PushYn을 Y로 할 경우, return한다")
	public void test3() {
		
		UserInfoOnPush resultOfQuery =givenCondition(true,true);
		assertDoesNotThrow(()-> validMng.validSingleUserInfo(voOfAndroidPushWithSingle.getTarget().get(0)));



	}
	
	private void seeIfMapperInvokedAndInsertDbForLoadingHistory(MsgFromKafkaVo vo) {
		Mockito.verify(mongo, Mockito.times(1)).insertDbHistory(ArgumentMatchers.any(ResultOfPush.class));
	}

	
	

	private UserInfoOnPush givenCondition(boolean validation, boolean pushYn) {
		UserInfoOnPush resultOfQuery = Mockito.mock(UserInfoOnPush.class);
		
		Mockito.when(mapper.getIfSendYnByUserNo(voOfAndroidPushWithSingle.getTarget().get(0))).thenReturn(resultOfQuery);
		Mockito.when(resultOfQuery.validation()).thenReturn(validation);
		Mockito.when(resultOfQuery.getPushYn()).thenReturn(pushYn);
		
		return resultOfQuery;
	}

	
	@Test
	@DisplayName("다수의 registrations lists의 수신 여부를 확인한 이후, y를 한 유저 정보만 남긴다")
	public void test4() {
		
		String[] list = {"noUserInfo-test4","invalidUser1-test4","invalidUser2-test4","invalidUser3-test4","validUser1-test4","validUser2-test4","validUser3-test4"};
		UserInfoOnPush resultOfQueryWithOk = Mockito.mock(UserInfoOnPush.class);
		UserInfoOnPush resultOfQueryWithNo = Mockito.mock(UserInfoOnPush.class);

		int cnt = 0;
		
		for(String item : list) {
			
			if(item.startsWith("i")) {
				Mockito.when(mapper.getIfSendYnByUserNo(item)).thenReturn(resultOfQueryWithNo);
				Mockito.when(resultOfQueryWithNo.validation()).thenReturn(true);
				Mockito.when(resultOfQueryWithNo.getPushYn()).thenReturn(false);
				
			}else if(item.startsWith("n")){
				
				Mockito.when(mapper.getIfSendYnByUserNo(item)).thenReturn(resultOfQueryWithNo);
				Mockito.when(resultOfQueryWithNo.validation()).thenReturn(false);
				Mockito.when(resultOfQueryWithNo.getPushYn()).thenReturn(false);
				
				
			}else {
				
				Mockito.when(mapper.getIfSendYnByUserNo(item)).thenReturn(resultOfQueryWithOk);
				Mockito.when(resultOfQueryWithOk.validation()).thenReturn(true);
				Mockito.when(resultOfQueryWithOk.getPushYn()).thenReturn(true);
				
			}
			try {
			validMng.validSingleUserInfo(item);
			}catch(RuntimeException e) {
				cnt++;
			}
		}
		
		assertEquals(cnt, 4);
	}
	
	@Test
	@DisplayName("다수의 registrations lists의 수신 여부를 확인한 이후, y를 한 유저 정보만 남긴다 - multi")
	public void test5() {
		List<String> mockTargets =  Arrays.asList("noUserInfo-test4","invalidUser1-test4","invalidUser2-test4","invalidUser3-test4","validUser1-test4","validUser2-test4","validUser3-test4");
		MsgFromKafkaVo vo = MsgFromKafkaVo
			.builder()
			.target(mockTargets)
			.build();
		
		UserInfoOnPush resultOfQueryWithOk = Mockito.mock(UserInfoOnPush.class);
		UserInfoOnPush resultOfQueryWithNo = Mockito.mock(UserInfoOnPush.class);
	
		validMng.validMultiUserInfo(vo);
		
	}
}
