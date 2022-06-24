package onnuripay.properties;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;

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
 * @see https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html
 * @apiNote <p>
 * 				batchProducer 설정 시 , ProducerConfig.LINGER_MS_CONFIG , ProducerConfig.BUFFER_MEMORY_CONFIG </br>
 * 				ProducerConfig.BATCH_SIZE_CONFIG, ProducerConfig.COMPRESSION_TYPE_CONFIG, ProducerConfig.MAX_REQUEST_SIZE_CONFIG 설정이 중요함.
 * 			</p>
 */
public class DefaultPropertiesWrapperForBatchProducer extends DefaultPropertiesWrapper{

	private final Properties batchProp;
	
	public DefaultPropertiesWrapperForBatchProducer(Properties prop) {
		super(prop);
		
		this.batchProp = new Properties(this.getDefaultSuperTypeProp());
		
		this.batchProp.putAll(prop);
		
	
		if(this.batchProp.get(ProducerConfig.CLIENT_ID_CONFIG) == null) this.batchProp.put(ProducerConfig.CLIENT_ID_CONFIG, DefaultConfigValue.DEFAULT_PRODUCER_ID+this.getClass().getPackageName());
		if(this.batchProp.get(ProducerConfig.LINGER_MS_CONFIG) == null) this.batchProp.put(ProducerConfig.LINGER_MS_CONFIG, DefaultConfigValue.LINGER_MS_FOR_BATCH);
		if(this.batchProp.get(ProducerConfig.BUFFER_MEMORY_CONFIG) == null) this.batchProp.put(ProducerConfig.BUFFER_MEMORY_CONFIG, DefaultConfigValue.BUFFER_MEMORY);
		if(this.batchProp.get(ProducerConfig.BATCH_SIZE_CONFIG) == null) this.batchProp.put(ProducerConfig.BATCH_SIZE_CONFIG, DefaultConfigValue.BATCH_SIZE);
		if(this.batchProp.get(ProducerConfig.COMPRESSION_TYPE_CONFIG) == null) this.batchProp.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, DefaultConfigValue.DEFAULT_BATCH_COMPRESSION);
		if(this.batchProp.get(ProducerConfig.MAX_REQUEST_SIZE_CONFIG)==null) this.batchProp.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, DefaultConfigValue.DEFAULT_MAX_REQUEST_SIZE);
		
		this.setProp(batchProp);
	}
	
	public Properties getBatchProp() {
		return batchProp;
	}

	
	
	
}
