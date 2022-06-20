package com.kafka.kafkanetty.client.test.manager;

import org.junit.jupiter.api.DisplayName;



@DisplayName("msg 파싱 벨리데이션 매니저 테스트 - VAlidation 기능을 Producer에서 처리함에 따라 기능 삭제")
/**
 * VAlidation 기능을 Producer에서 처리함에 따라 기능 삭제
 * 
 * **/
public class ValidationManagerTest {

//	SmsPushMapper mapper = TestUtil.mapper;
//	TempMongodbTemplate mongo = TestUtil.mongo;
//	
//	ValidationManager validMng = new ValidationManagerImpl(mapper,mongo);
//	
//	MsgFromKafkaVo voOfAndroidPushWithSingle = MsgFromKafkaAndroid.voForSinglePushWithValidDataBody;
//	MsgFromKafkaVo voForAndroidWithMutliple = MsgFromKafkaAndroid.voForMultiplePushWithValidDataBody;
//	
//	String prefixForInvalid = "i";
//	String prefixForNoUser = "n";
//	String prefixForValid = "v";
//	
//	List<String> mockTargets = Arrays.asList(prefixForNoUser+"noUserInfo-test4",prefixForInvalid+"invalidUser1-test4",prefixForInvalid+"invalidUser2-test4",prefixForInvalid+"invalidUser3-test4",prefixForValid+"validUser1-test4",prefixForValid+"validUser2-test4",prefixForValid+"validUser3-test4");
//
//	
//	@AfterEach
//	public void cleanUp() {
//		Mockito.reset(mongo);
//		Mockito.reset(mapper);
//	}
//
//	@Test
//	@DisplayName("User 정보가 Invalid할 경우, 실패한 User 정보를 몽고DB에 입력한다.")
//	public void test1() {
//		UserInfoOnPush resultOfQuery= givenCondition(false,true);
//
//		assertThrows(UserInfoInvalidException.class, ()->validMng.validSingleUserInfo(voOfAndroidPushWithSingle.getTarget().get(0)));
//
//		seeIfMapperInvokedAndInsertDbForLoadingHistory(voOfAndroidPushWithSingle);
//		
//	}
//
//
//	
//	@Test
//	@DisplayName("User가 PushYn을 n으로 할 경우 실패한 User 정보를 몽고DB에 입력한다.")
//	public void test2() {
//		
//		UserInfoOnPush resultOfQuery = givenCondition(true,false);
//		
//		assertThrows(UserNotAllowNotificationException.class, ()->validMng.validSingleUserInfo(voOfAndroidPushWithSingle.getTarget().get(0)));
//
//
//
//
//	}
//
//
//	@Test
//	@DisplayName("User 정보가 valid하고, PushYn을 Y로 할 경우, return한다")
//	public void test3() {
//		
//		UserInfoOnPush resultOfQuery =givenCondition(true,true);
//		assertDoesNotThrow(()-> validMng.validSingleUserInfo(voOfAndroidPushWithSingle.getTarget().get(0)));
//
//
//
//	}
//	
//	private void seeIfMapperInvokedAndInsertDbForLoadingHistory(MsgFromKafkaVo vo) {
//		Mockito.verify(mongo, Mockito.times(1)).insertDbHistory(ArgumentMatchers.any(ResultOfPush.class));
//	}
//
//	
//	
//
//	private UserInfoOnPush givenCondition(boolean validation, boolean pushYn) {
//		UserInfoOnPush resultOfQuery = Mockito.mock(UserInfoOnPush.class);
//		
//		Mockito.when(mapper.getIfSendYnByUserNo(voOfAndroidPushWithSingle.getTarget().get(0))).thenReturn(resultOfQuery);
//		Mockito.when(resultOfQuery.validation()).thenReturn(validation);
//		Mockito.when(resultOfQuery.getPushYn()).thenReturn(pushYn);
//		
//		return resultOfQuery;
//	}
//
//	
//	@Test
//	@DisplayName("다수의 registrations lists의 수신 여부를 확인한 이후, y를 한 유저 정보만 남긴다")
//	public void test4() {
//		
//
//		int cnt = 0;
//		
//		for(String item : mockTargets) {
//			
//			givenCondition(item);
//			
//			try {
//				validMng.validSingleUserInfo(item);
//			}catch(RuntimeException e) {
//				cnt++;
//			}
//		}
//		
//		assertEquals(cnt, 4);
//	}
//
//	
//	@Test
//	@DisplayName("다수의 registrations lists의 수신 여부를 확인한 이후, y를 한 유저 정보만 남긴다 - multi")
//	public void test5() {
//		
//		long size = mockTargets.stream().filter(a->a.startsWith("v")).count();
//		
//		
//		MsgFromKafkaVo mockVo = TestUtil.createMsgVoForSMS(DataBodys.bodyOfWithValidHeaderAndBody, TypeOfSending.MULTIPLE, mockTargets);
//
//		for(String mock : mockTargets) {
//			givenCondition(mock);
//
//		}
//		
//		mockVo =  validMng.validMultiUserInfo(mockVo);
//		long result = mockVo.getTarget().stream().filter(a->a.startsWith("v")).count();
//		
//		assertEquals(mockVo.getTarget().size(), size);
//		assertEquals(size, result);
//		System.out.println(mockVo);
//		
//	}
//	
//	private void givenCondition(String item) {
//		
//		
//		UserInfoOnPush resultOfQueryWithOk = Mockito.mock(UserInfoOnPush.class);
//		UserInfoOnPush resultOfQueryWithNo = Mockito.mock(UserInfoOnPush.class);
//		
//		if(item.startsWith("i")) {
//			Mockito.when(mapper.getIfSendYnByUserNo(item)).thenReturn(resultOfQueryWithNo);
//			Mockito.when(resultOfQueryWithNo.validation()).thenReturn(true);
//			Mockito.when(resultOfQueryWithNo.getPushYn()).thenReturn(false);
//			
//		}else if(item.startsWith("n")){
//			
//			Mockito.when(mapper.getIfSendYnByUserNo(item)).thenReturn(resultOfQueryWithNo);
//			Mockito.when(resultOfQueryWithNo.validation()).thenReturn(false);
//			Mockito.when(resultOfQueryWithNo.getPushYn()).thenReturn(false);
//			
//			
//		}else {
//			
//			Mockito.when(mapper.getIfSendYnByUserNo(item)).thenReturn(resultOfQueryWithOk);
//			Mockito.when(resultOfQueryWithOk.validation()).thenReturn(true);
//			Mockito.when(resultOfQueryWithOk.getPushYn()).thenReturn(true);
//			
//		}
//	}
//	
}
