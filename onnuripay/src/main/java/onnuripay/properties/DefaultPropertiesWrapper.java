package onnuripay.properties;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;

import onnuripay.consts.CommonMsg;
import onnuripay.consts.DefaultConfigValue;

/**
 * 
 * KT OnnuriPay version 1.0
 *
 *  Copyright ⓒ 2022 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */


/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 6. 24.
 * @apiNote 프로듀서 초기화를 위한 기본 설정 wrapper 객체
 * @see https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html 
 * @implSpec <p>
 * 				(VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.ByteArraySerializer); </br> //값 직렬화 클래스
				(KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer);	</br> //키 직렬화 클래스
				(PARTITIONER_CLASS_CONFIG, org.apache.kafka.clients.producer.UniformStickyPartitioner);	</br> // 파티셔닝 전략 기본.
 * 			</p>
 */
public class DefaultPropertiesWrapper extends PropertiesWrapper{

	private final Properties prop;

	public DefaultPropertiesWrapper(Properties prop) {
		
		this.prop = new Properties(prop);
		System.out.println(this.prop);
		if(this.prop.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG) == null) throw new IllegalArgumentException(CommonMsg.BROKER_SERVERINFO_NOT_EXIST);
		if(!(this.prop.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG) instanceof List<?>)) throw new IllegalArgumentException(CommonMsg.BROKERS_FORMAT_ERROR);
		
		this.prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBrokerList());
		
		if(this.prop.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)==null) this.prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DefaultConfigValue.DEFAULT_VALUE_SERIALIZER);
		if(this.prop.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG)==null) this.prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, DefaultConfigValue.DEFAULT_KEY_SERIALIZER);
		if(this.prop.get(ProducerConfig.PARTITIONER_CLASS_CONFIG)==null) this.prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultConfigValue.DEFAULT_PARTIOTION_STRATEGY);	
		
		this.setProp(prop);
			
	}

	private String getBrokerList() {
		@SuppressWarnings("unchecked")
		List<String> brokers = (List<String>) this.prop.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG);
		String result = brokers.stream().reduce((initial,broker)->initial+","+broker).get();
		return result;
	}
	
	public Properties getDefaultSuperTypeProp() {
		return this.prop;
	}
	
	
	
	
	
	
}
