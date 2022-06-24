package onnuripay.consts;


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
 * @implNote <p> 기본 설정 상수 클래스. 크기는 byte가 기본단위이며, 시간은 ms </p>
 */
public class DefaultConfigValue {

	public static final Object DEFAULT_PRODUCER_ID = "default_producer : ";
	public static final String DEFAULT_VALUE_SERIALIZER = "org.apache.kafka.common.serialization.ByteArraySerializer";
	public static final String DEFAULT_KEY_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";

	
	public static final String DEFAULT_CHARSET = "utf-8";
	public static final Object DEFAULT_PARTIOTION_STRATEGY = "org.apache.kafka.clients.producer.UniformStickyPartitioner";
	public static final Object LINGER_MS_FOR_BATCH = "5"; //Batch 처리 시, 설정된 배치 레코드 수만큼 차지 않으 경우 대기하는 최대 시간. ms 단위.
	public static final Object BUFFER_MEMORY = 33554432;
	public static final Object BATCH_SIZE = 16384;
	public static final Object DEFAULT_BATCH_COMPRESSION = "gzip";
	public static final Object DEFAULT_MAX_REQUEST_SIZE = 1048576;
	

}
