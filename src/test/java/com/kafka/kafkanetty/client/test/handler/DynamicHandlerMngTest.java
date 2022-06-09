package com.kafka.kafkanetty.client.test.handler;

import static org.mockito.Mockito.atLeast;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.client.handler.manager.impl.PushMultipleManager;
import com.kafka.kafkanetty.client.handler.manager.impl.PushSingleManager;
import com.kafka.kafkanetty.client.handler.manager.impl.SmsMultipleManager;
import com.kafka.kafkanetty.client.handler.manager.impl.SmsSingleManager;
import com.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import util.TestUtil;

@DisplayName("MSG 타입(SMS, PUSH), 종류(대량, 단건)에 따라 목적으로 하는 객체가 Invoked 하는지")
public class DynamicHandlerMngTest {
	
	FirebaseMessaging instance = TestUtil.instance;
	SmsPushMapper mapper = TestUtil.mapper;


	
	SendManager smsSingle = Mockito.spy(new SmsSingleManager());
	SendManager smsMulti = Mockito.spy(new SmsMultipleManager());
	SendManager pushSingle = Mockito.spy(new PushSingleManager(instance,mapper));
	SendManager pushMulti = Mockito.spy(new PushMultipleManager(instance));


	DynamicHandlerManager manager =  new DynamicHandlerManager(smsSingle, smsMulti, pushSingle, pushMulti);
	
	MsgFromKafkaVo voForMultipleSMS = TestUtil.voForMultipleSMS;
	
	MsgFromKafkaVo voForSingleSMS = TestUtil.voForSingleSMS;
	
	MsgFromKafkaVo voForMultiplePush =  TestUtil.voForMultiplePush;
	
	MsgFromKafkaVo voForSinglePush =  TestUtil.voForSinglePush;
	
	

	
	@Test
	@DisplayName("SMS 대량 발송 테스트")
	public void test() {
	
		manager.consume(voForMultipleSMS);
		Mockito.verify(smsMulti, atLeast(1)).send(voForMultipleSMS);
		
	
	}
	
	@Test
	@DisplayName("SMS 단건 발송 테스트")
	public void test1() {
	
		manager.consume(voForSingleSMS);
		Mockito.verify(smsSingle, atLeast(1)).send(voForSingleSMS);

		
	
	}
	
	@Test
	@DisplayName("PUSH 대량 발송 테스트")
	public void test2() {
	
		manager.consume(voForMultiplePush);
		Mockito.verify(pushMulti, atLeast(1)).send(voForMultiplePush);

	
	}
	
	@Test
	@DisplayName("PUSH 단건 발송 테스트")
	public void test3() {
		
		Mockito.when(mapper.getIfSendYnByUserNo(ArgumentMatchers.anyString())).thenReturn(true);
	
		manager.consume(voForSinglePush);
		Mockito.verify(pushSingle, atLeast(1)).send(voForSinglePush);

	}
}
