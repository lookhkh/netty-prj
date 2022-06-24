package onnuripay;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import onnuripay.properties.DefaultPropertiesWrapperForBatchProducer;
import onnuripay.properties.PropertiesWrapper;


public class ProducerTest {

	Properties prop = new Properties();
	PropertiesWrapper wrapperProp;
	ProducerWrapper wrapper;
	
	@BeforeEach
	public void init() {
		
	prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Arrays.asList("localhost:9092"));
	
	this.wrapperProp = new DefaultPropertiesWrapperForBatchProducer(prop);
	
	wrapper = new ProducerWrapper(wrapperProp, wrapperProp, null);
	}
	
	@Test
	public void test() {
		assertTrue(true);
	}
	
}
