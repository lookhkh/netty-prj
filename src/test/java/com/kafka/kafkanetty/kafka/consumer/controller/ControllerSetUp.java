package com.kafka.kafkanetty.kafka.consumer.controller;

import org.mockito.Mockito;

import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherController;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherControllerImpl;
import com.kt.onnuipay.kafka.kafkanetty.kafka.dynamic.DynamicHandlerManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

import lombok.Data;
import util.TestSeUpAbstract;

@Data
public class ControllerSetUp extends TestSeUpAbstract{
	
	public KafkaMsgParser mockParser = Mockito.mock(KafkaMsgParser.class);
	public DynamicHandlerManager mockHanlder = Mockito.mock(DynamicHandlerManager.class);
	public TempMongodbTemplate mockDb = Mockito.mock(TempMongodbTemplate.class);
	
	public DispatcherController controller = new DispatcherControllerImpl(mockParser,mockHanlder,mockDb);

	
	


	@Override
	public void reset() {
		this.mockParser = Mockito.mock(KafkaMsgParser.class);
		this.mockHanlder = Mockito.mock(DynamicHandlerManager.class);
		this.mockDb = Mockito.mock(TempMongodbTemplate.class);		
	}
	
}
