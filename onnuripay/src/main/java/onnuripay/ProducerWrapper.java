package onnuripay;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import datavo.msg.MessageWrapper;
import onnuripay.consts.Topics;
import onnuripay.properties.DefaultPropertiesWrapperForBatchProducer;
import onnuripay.properties.DefaultPropertiesWrapperForSingleProducer;
import onnuripay.properties.PropertiesWrapper;
import onnuripay.validation.ValidatorForVo;

/**
 * @author Cho hyun il lookhkh37@daonlink.com
 * @implNote ProducerWrapper클래스, 해당 클래스를 정의한 이후, produce한다.
 * 
 * **/
public class ProducerWrapper {	
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Properties propsForSingle;
    private final Properties propsForMulti;
    private final Producer<String, byte[]> producerForSingle;
    private final Producer<String, byte[]> producerForMultiple;
    
    private final ValidatorForVo validator;

    public ProducerWrapper(PropertiesWrapper propsForSingle, PropertiesWrapper propsForMulti, ValidatorForVo val) {
		this.propsForSingle = propsForSingle.getConfigProp();
		this.propsForMulti = propsForMulti.getConfigProp();

		producerForSingle = new KafkaProducer<String, byte[]>(this.propsForSingle);
		producerForMultiple = new KafkaProducer<String, byte[]>(this.propsForMulti);
		
		this.validator = val;
		
		
	}
	

	public boolean send(MessageWrapper data, Callback callback) {
		
		byte[] json = this.validator.validate(data);
		
		ProducerRecord<String, byte[]> record = new ProducerRecord<String, byte[]>(Topics.SINGLE_SEND_TOPICS,  json);

		producerForSingle.send(record, callback);
		
		return true;
	}
	
	public static void main(String[] args) {
		Properties pro = new Properties();
		pro.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Arrays.asList("localhost:9092"));
		System.out.println(pro);
		PropertiesWrapper batch = new DefaultPropertiesWrapperForBatchProducer(pro);
		PropertiesWrapper single = new DefaultPropertiesWrapperForSingleProducer(pro);

	
		ProducerWrapper wrapper = new ProducerWrapper(single, batch, null);
	}
	

	
//	public boolean sendAll(String json , Callback callback) {
//		
//		ProducerRecord<String, String> record = new ProducerRecord<String, String>(Topics.MULTI_SEND_TOPICS, null, json);
//		try {
//			producerForMultiple.send(record, callback);
//			return true;
//
//		}catch(Exception e) {
//			
//			e.printStackTrace();
//			return false;
//			
//		}

	
	

    
}
