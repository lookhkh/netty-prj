package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import com.google.firebase.database.util.JsonMapper;
import com.google.gson.JsonObject;
import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParserImpl;

import datavo.msg.SingleMessageWrapper;
import util.MsgFromKafkaAndroid;

@DisplayName("메세지 파서 테스트")
public class MsgParserTest {

	ObjectMapper mapper = new ObjectMapper();
	
	KafkaMsgParser parser = new KafkaMsgParserImpl();

	SingleMessageWrapper vo = datavo.testUtils.MsgFromKafkaAndroid.voForSinglePushWithValidDataBody;
	
	@Test
	@DisplayName("parser에 잘못된 형식의 데이터를 집어넣을 경우 JsonDataProcessingWrapperException이 터진다.")
	public void test52() {
		assertThrows(JsonDataProcessingWrapperException.class, () -> parser.parse("hi", SingleMessageWrapper.class));
	}
	
	@Test
	@DisplayName("parse에 JSON 값이 입력되면, Metadata 파싱 후, 타입을 결정한다.")
	public void test1() throws IOException {
		String json = JsonMapper.serializeJson(vo);
		System.out.println(json);
		SingleMessageWrapper result =  parser.parse(json, SingleMessageWrapper.class);
		
		assertNotNull(result);
	}	
	
//	@Test
//	@DisplayName("올바른 JSON String이 들어온 경우, 파싱에 성공하며, MsgFromKafka VO를 반환한다.")
//	public void test53() throws JsonProcessingException  {
//		
//		
//		
//		String value = mapper.writeValueAsString(vo);
//		System.out.println(value);
//		MsgFromKafkaVo convertedVoFromJsonString = parser.parse(value);
//		
//		assertAll(
//				()->assertEquals(convertedVoFromJsonString.getActionUrl(), vo.getActionUrl()),
//				()->assertEquals(convertedVoFromJsonString.getCodeOfType(), vo.getCodeOfType()),
//				()->assertEquals(convertedVoFromJsonString.getSender(), vo.getSender()),
//				()->assertEquals(convertedVoFromJsonString.getTimeOfDelievery(), vo.getTimeOfDelievery()),
//				()->assertEquals(convertedVoFromJsonString.getTypeValue(), vo.getTypeValue())
//				);
//		
//		
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Test
//	@DisplayName("파서에서 어떤 예상치 못한 에러가 터져도, RuntimeException으로 래핑하여 위로 던진다.")
//	public void test3() throws JsonProcessingException {
//		ObjectMapper mockingMapper = Mockito.mock(ObjectMapper.class);
//		KafkaMsgParserImpl parserWithMocking = new KafkaMsgParserImpl();
//		parserWithMocking.setObjectMapper(mockingMapper);
//		
//		String value = mapper.writeValueAsString(vo);
//
//		
//		Mockito.when(parserWithMocking.parse(value)).thenThrow(IllegalArgumentException.class, NullPointerException.class, IndexOutOfBoundsException.class);
//		assertThrows(RuntimeException.class, ()-> parserWithMocking.parse(ArgumentMatchers.anyString()));
//
//	}
	
	
	
	
}
