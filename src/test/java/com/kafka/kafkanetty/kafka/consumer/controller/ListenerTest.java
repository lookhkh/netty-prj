package com.kafka.kafkanetty.kafka.consumer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.onnuipay.KafkaNettyApplication;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherController;
import com.kt.onnuipay.kafka.kafkanetty.kafka.listener.AckMessageListener;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;

import lombok.extern.slf4j.Slf4j;
import util.MsgFromKafkaAndroid;
import util.MsgFromKafkaIOS;
import util.MsgFromKafkaSmss;

@Slf4j
@ContextConfiguration(classes = KafkaNettyApplication.class)
@SpringBootTest(properties = {"spring.profiles.active: test"})
@DisplayName("통합 테스트 용, 현재는 parser 부분이 미개발 중이라 계속 에러 나올 것임 220609 조현일")
@EmbeddedKafka(topics = "hello.kafka",ports = {9092})
public class ListenerTest {

	@Autowired
	DispatcherController cont;
	@Autowired
	@Qualifier("single")
	ExecutorService service;
	
	
	AckMessageListener listener = new AckMessageListener(cont,service);
	
	
	@Test
	@DisplayName("통합 테스트 용, 현재는 parser 부분이 미개발 중이라 계속 에러 나올 것임 220609 조현일")
	public void test() {
		
		ObjectMapper ob = new ObjectMapper();
		
		List<MsgFromKafkaVo> failedList = Arrays.asList(	
				MsgFromKafkaAndroid.voForAndroidWithInvalidHeaderAndInvalidBody,
				MsgFromKafkaAndroid.voForSinglePushWithInvalidHeader,
				MsgFromKafkaIOS.voForIOSWithInvalidHeaderAndInvalidBody,
				MsgFromKafkaIOS.voForSinglePushWithInvalidBody,
				MsgFromKafkaIOS.voForSinglePushWithInvalidHeader
//				MsgFromKafkaSmss.voForMultipleSMSWithInValidDataBodyWithInvalidBody,
//				MsgFromKafkaSmss.voForMultipleSMSWithInValidDataBodyWithInvalidHeader,
//				MsgFromKafkaSmss.voForSingleSMSWithInValidDataBodyIwthInValidHeader,
//				MsgFromKafkaSmss.voForSingleSMSWithInvalidHeaderAndInvalidBody
				);
		
		List<MsgFromKafkaVo> successList = Arrays.asList(	
				MsgFromKafkaAndroid.voForSinglePushWithValidDataBody,
				MsgFromKafkaAndroid.voForMultiplePushWithValidDataBody,
				MsgFromKafkaIOS.voForSinglePushWithValidDataBody
//				MsgFromKafkaSmss.voForMultipleSMSWithValidDataBody,
//				MsgFromKafkaSmss.voForSingleSmsWithValidDataBody
				);

		List<MsgFromKafkaVo> vo = new ArrayList<>();
		vo.addAll(failedList);
		vo.addAll(successList);
		
		assertTrue(vo.size()>0);
		
		List<String> jsons =  vo.stream().map(v->{
			try {
				return ob.writeValueAsString(v);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		})
				.collect(Collectors.toList());
		
		assertEquals(jsons.size(), vo.size());
		
		
		List<ResultOfPush> results =  jsons.stream().map(v->cont.route(v))
				.collect(Collectors.toList());
		
		
		log.info("{},sadasd",results);
		
		assertTrue(results.size()>0);

		
		
		List<ResultOfPush> resultsOfNull = results.stream().filter(item ->item==null).collect(Collectors.toList());
		
		assertEquals(resultsOfNull.size() , 0);
		List<ResultOfPush> resultsOfSuccess = results.stream().filter(item ->item.isSuccess()).collect(Collectors.toList());
		List<ResultOfPush> resultsOfFail = results.stream().filter(item -> !item.isSuccess()).collect(Collectors.toList());

		
		assertEquals(resultsOfSuccess.size(), successList.size());
		assertEquals(resultsOfFail.size(), failedList.size());

		
		
		
		//TestUtil.getDatas().stream().forEach(str ->cont.route(String.valueOf(str)));
		
	}
	
}
