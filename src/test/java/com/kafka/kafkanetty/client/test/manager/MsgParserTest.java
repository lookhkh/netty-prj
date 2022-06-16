package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.DataBody;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.TypeOfSending;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParserImpl;

import util.DataBodys;
import util.MsgFromKafkaAndroid;
import util.TestUtil;

@DisplayName("메세지 파서 테스트")
public class MsgParserTest {

	ObjectMapper mapper = new ObjectMapper();
	List<DataBody> bodyOfMultiplePush = DataBodys.bodyOfWithValidHeaderAndBody;
	
	KafkaMsgParser parser = new KafkaMsgParserImpl();

	
	
	@Test
	@DisplayName("SMS, 제목 30bytes 미만, 내용 2000 미만일 경우, 성공적으로 파싱된다.")
	public void test() throws JsonProcessingException {
		
		Map<Boolean, List<DataBody>> result = extracted(KafkaKeyEnum.SMS,DataBody.builder().title("title").body("body").build());
		
		assertEquals(result.get(true).size(), 1);

	}

	
	@Test
	@DisplayName("SMS, 제목 30bytes 미만, 내용 200 이상일 경우, 성공적으로 파싱된다.")
	public void test1_1() throws JsonProcessingException {


		Map<Boolean, List<DataBody>> result = extracted(KafkaKeyEnum.SMS,DataBody.builder().title("title").body("body".repeat(55)).build());
		
		assertEquals(result.get(true).size(), 1);

	}
	
	@Test
	@DisplayName("SMS, 제목 30bytes 이상일 경우, 내용 2000 미만일 경우, 파싱에 실패한다.")
	public void test3() throws JsonProcessingException {

		Map<Boolean, List<DataBody>> result  = extracted(KafkaKeyEnum.SMS, DataBody.builder().title("title is too long for the limit of 20bytes").body("body").build());
		
		assertNull(result.get(true));
		assertEquals(result.get(false).size(), 1);

	}
	
	@Test
	@DisplayName("SMS, 제목 30bytes 미만일 경우, 내용 2000 이상일 경우, 파싱에 실패한다.")
	public void test2() throws JsonProcessingException {
		String tooLongBody = "abc".repeat(2000);


		Map<Boolean, List<DataBody>> result = extracted(KafkaKeyEnum.SMS, DataBody.builder().title("title").body(tooLongBody).build());
		
		assertNull(result.get(true));
		assertEquals(result.get(false).size(), 1);

	}
	
	@Test
	@DisplayName("PUSH, 제목 30bytes 미만일 경우, 내용 200미만일 경우 파싱에 성공한다.")
	public void test4() throws JsonProcessingException {

		
		Map<Boolean, List<DataBody>> result = extracted(KafkaKeyEnum.ANDROID,DataBody.builder().title("title").body("body").build());
		
		assertEquals(result.get(true).size(), 1);

	}
	
	@Test
	@DisplayName("PUSH, 제목 30bytes 미만일 경우, 내용 200이상일 경우 파싱에 실패한다.")
	public void test4_1() throws JsonProcessingException {

		System.out.println("body".repeat(50).getBytes().length);
		

		
		Map<Boolean, List<DataBody>> result = extracted(KafkaKeyEnum.ANDROID,DataBody.builder().title("title").body("body".repeat(55)).build());

		assertNull(result.get(true));
		assertEquals(result.get(false).size(), 1);
		
		assertEquals(result.get(false).get(0).getBody(), "body".repeat(55));

	}
	
	@Test
	@DisplayName("PUSH, 제목 30bytes 미만일 경우, 내용 200 bytes미만일 경우, 토큰의 수가 500 초과이면 파싱에 실패한다.")
	public void test4_2() throws JsonProcessingException {

		List<String> tokens = new ArrayList<>();
		
		for(int i=0; i<502; i++) {
			tokens.add(String.valueOf(i));
		}
		
		System.out.println("body".repeat(50).getBytes().length);
		
		
		
		MsgFromKafkaVo vo = MsgFromKafkaVo.builder()
								.sender("123abc")
								.actionUrl("www.log.com")
								.isScheduled(false)
								.key(KafkaKeyEnum.ANDROID)
								.timeOfDelievery("2022-06-06 22:05:44")
								.payload(bodyOfMultiplePush)
								.target(tokens)
								.type(MsgType.APP_PUSH)
								.build();
		Map<Boolean, List<DataBody>> result =  vo.validateDataBodys();
		
		assertEquals(result.get(false).size(), 1);
		
		//assertEquals(result.get(false).get(0).getBody(), "body");

	}
	
	@Test
	@DisplayName("parser에 잘못된 형식의 데이터를 집어넣을 경우 IllegalArgumentException이 터진다.")
	public void test52() {
		assertThrows(IllegalArgumentException.class, () -> parser.parse("hi"));
	}
	
	@Test
	@DisplayName("올바른 JSON String이 들어온 경우, 파싱에 성공하며, MsgFromKafka VO를 반환한다.")
	public void test53() throws JsonProcessingException  {
		
		MsgFromKafkaVo test = MsgFromKafkaVo.builder()
				.sender("123abc")
				.actionUrl("www.log.com")
				.isScheduled(false)
				.key(KafkaKeyEnum.ANDROID)
				.timeOfDelievery("2022-06-06")
				.payload(bodyOfMultiplePush)
				.target(Arrays.asList("hi"))
				.type(MsgType.APP_PUSH)
				
				.kind(TypeOfSending.SINGLE)
				.build();
		
		String value = mapper.writeValueAsString(MsgFromKafkaAndroid.voForSinglePushWithValidDataBody);
		System.out.println(value);
		MsgFromKafkaVo convertedVoFromJsonString = parser.parse(value);
		
		assertAll(
				()->assertEquals(convertedVoFromJsonString.getActionUrl(), test.getActionUrl()),
				()->assertEquals(convertedVoFromJsonString.getCodeOfType(), test.getCodeOfType()),
				()->assertEquals(convertedVoFromJsonString.getSender(), test.getSender()),
				()->assertEquals(convertedVoFromJsonString.getTimeOfDelievery(), test.getTimeOfDelievery()),
				()->assertEquals(convertedVoFromJsonString.getTypeValue(), test.getTypeValue())
				);
		
		
	}
	
	private Map<Boolean, List<DataBody>> extracted(KafkaKeyEnum key, DataBody b) {
		MsgFromKafkaVo vo = MsgFromKafkaVo.builder()
								.sender("123abc")
								.actionUrl("www.log.com")
								.isScheduled(false)
								.key(key)
								.timeOfDelievery("2022-06-06")
								.payload(Arrays.asList(b))
								.target(Arrays.asList("abc"))
								.type(MsgType.APP_PUSH)
								.build();
		Map<Boolean, List<DataBody>> result =  vo.validateDataBodys();
		return result;
	}
	
	
	
}
