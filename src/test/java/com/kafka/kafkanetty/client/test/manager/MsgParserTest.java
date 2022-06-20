package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.rpc.OutOfRangeException;
import com.kt.onnuipay.kafka.kafkanetty.exception.JsonDataProcessingWrapperException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.DataBody;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.TypeOfSending;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParserImpl;

import util.DataBodys;
import util.MsgFromKafkaAndroid;

@DisplayName("메세지 파서 테스트")
public class MsgParserTest {

	ObjectMapper mapper = new ObjectMapper();
	List<DataBody> bodyOfMultiplePush = DataBodys.bodyOfWithValidHeaderAndBody;
	
	KafkaMsgParser parser = new KafkaMsgParserImpl();

	MsgFromKafkaVo vo = MsgFromKafkaAndroid.voForSinglePushWithValidDataBody;
	
	@Test
	@DisplayName("parser에 잘못된 형식의 데이터를 집어넣을 경우 JsonDataProcessingWrapperException이 터진다.")
	public void test52() {
		assertThrows(JsonDataProcessingWrapperException.class, () -> parser.parse("hi"));
	}
	
	@Test
	@DisplayName("올바른 JSON String이 들어온 경우, 파싱에 성공하며, MsgFromKafka VO를 반환한다.")
	public void test53() throws JsonProcessingException  {
		
		
		
		String value = mapper.writeValueAsString(vo);
		System.out.println(value);
		MsgFromKafkaVo convertedVoFromJsonString = parser.parse(value);
		
		assertAll(
				()->assertEquals(convertedVoFromJsonString.getActionUrl(), vo.getActionUrl()),
				()->assertEquals(convertedVoFromJsonString.getCodeOfType(), vo.getCodeOfType()),
				()->assertEquals(convertedVoFromJsonString.getSender(), vo.getSender()),
				()->assertEquals(convertedVoFromJsonString.getTimeOfDelievery(), vo.getTimeOfDelievery()),
				()->assertEquals(convertedVoFromJsonString.getTypeValue(), vo.getTypeValue())
				);
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("파서에서 어떤 예상치 못한 에러가 터져도, RuntimeException으로 래핑하여 위로 던진다.")
	public void test3() throws JsonProcessingException {
		ObjectMapper mockingMapper = Mockito.mock(ObjectMapper.class);
		KafkaMsgParserImpl parserWithMocking = new KafkaMsgParserImpl();
		parserWithMocking.setObjectMapper(mockingMapper);
		
		String value = mapper.writeValueAsString(vo);

		
		Mockito.when(parserWithMocking.parse(value)).thenThrow(IllegalArgumentException.class, NullPointerException.class, IndexOutOfBoundsException.class);
		assertThrows(RuntimeException.class, ()-> parserWithMocking.parse(ArgumentMatchers.anyString()));

	}
	
	
	
	
}
