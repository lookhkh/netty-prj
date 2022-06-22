package com.kafka.kafkanetty.kafka.consumer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherController;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

import datavo.msg.MessageWrapper;
import datavo.testUtils.MsgFromKafkaAndroid;
import datavo.testUtils.MsgFromKafkaIOS;
import datavo.testUtils.MsgFromKafkaSmss;
import lombok.Data;
import util.TestUtil;


@Data
public class TestVos {
	public DynamicHandlerManager mockingDynamicHanlder;
	public KafkaMsgParser mockingParser;
	public TempMongodbTemplate mockingMongo;
	public DispatcherController controller;
	public ObjectMapper mapper;
	public MessageWrapper voForSingleAndroidPush;
	public MessageWrapper voForMultipleAndroidPush;
	public MessageWrapper voForSingleIOSPush;
	public MessageWrapper voForMultipleIOSPush;
	public MessageWrapper voForMultipleSMS;
	public MessageWrapper voForSingleSMS;
	public MessageWrapper voForMultipleLMS;
	public MessageWrapper voForSingleLMS;
	public String jsonForSingleAndroidPush;
	public String jsonForSingleIOSPush;
	public String jsonForSingleSMS;
	public String jsonForMultiAndroidPush;
	public String jsonForMultiIOSPush;
	public String jsonForMultiSMS;

	public TestVos(DynamicHandlerManager mockingDynamicHanlder, KafkaMsgParser mockingParser,
			TempMongodbTemplate mockingMongo, ObjectMapper mapper, MessageWrapper voForSingleAndroidPush,
			MessageWrapper voForMultipleAndroidPush, MessageWrapper voForSingleIOSPush,
			MessageWrapper voForMultipleIOSPush, MessageWrapper voForMultipleSMS, MessageWrapper voForSingleSMS,
			MessageWrapper voForMultipleLMS, MessageWrapper voForSingleLMS) {
		this.mockingDynamicHanlder = mockingDynamicHanlder;
		this.mockingParser = mockingParser;
		this.mockingMongo = mockingMongo;
		this.mapper = mapper;
		this.voForSingleAndroidPush = voForSingleAndroidPush;
		this.voForMultipleAndroidPush = voForMultipleAndroidPush;
		this.voForSingleIOSPush = voForSingleIOSPush;
		this.voForMultipleIOSPush = voForMultipleIOSPush;
		this.voForMultipleSMS = voForMultipleSMS;
		this.voForSingleSMS = voForSingleSMS;
		this.voForMultipleLMS = voForMultipleLMS;
		this.voForSingleLMS = voForSingleLMS;
		
		
	}
	
	public static TestVos getTestVos() {
		return 	new TestVos(TestUtil.mockingDynamicHanlder, TestUtil.mockingParser, TestUtil.mongo, new ObjectMapper(), MsgFromKafkaAndroid.voForSinglePushWithValidDataBody,
				MsgFromKafkaAndroid.voForMultiplePush, MsgFromKafkaIOS.voForSingleIOSPush, MsgFromKafkaIOS.voForMultipleIOSPush, MsgFromKafkaSmss.voForMultipleSMSWithSMS, MsgFromKafkaSmss.voForSingleSmsWithSMS, MsgFromKafkaSmss.voForMultipleSMSWithLMS,
				MsgFromKafkaSmss.voForSingleSmsWithLMS);
	}
}