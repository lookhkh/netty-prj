package onnuripay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import onnuripay.consts.CommonMsg;
import onnuripay.consts.DefaultConfigValue;
import onnuripay.properties.PropertiesWrapper;

public class PropWrapperTest {

	Properties prop = new Properties();
	List<String> brokerLists = Arrays.asList("localhost:9092","localhost:8080");
	
	@Test
	@DisplayName("server 정보를 입력하지 않을 경우, 에러를 반환한다.")
	public void test() {
		Throwable t =  assertThrows(IllegalArgumentException.class, ()->new PropertiesWrapper(prop));
		assertEquals(t.getMessage(), CommonMsg.BROKER_SERVERINFO_NOT_EXIST);
	}
	
	@DisplayName("client.id, value.serializer.class.config에 값을 입려하지 않을 경우, 기본값으로 설정된다.")
	@Test
	public void test1() {
		prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerLists);
		
		PropertiesWrapper wrapper = new PropertiesWrapper(prop);
		
		Properties newP = wrapper.getProp();
		
		assertNotSame(wrapper.getProp(), prop);
		assertEquals(newP.get(ProducerConfig.CLIENT_ID_CONFIG), DefaultConfigValue.DEFAULT_PRODUCER_ID);
		assertEquals(newP.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG), DefaultConfigValue.DEFAULT_VALUE_SERIALIZER );
		assertEquals(newP.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG), "localhost:9092,localhost:8080");
		
	}
	
	@Test
	@DisplayName("서버 브로커 정보를 List로 입력하지 않을 경우 에러를 반환한다.")
	public void test2() {
		prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:8080");
		Throwable t=  assertThrows(IllegalArgumentException.class, ()-> new PropertiesWrapper(prop));
		
		assertEquals(t.getMessage(), CommonMsg.BROKERS_FORMAT_ERROR);


	}
}
