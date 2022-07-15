package com.kafka.consumer.controller;

import org.mockito.Mockito;

import com.kt.onnuripay.kafka.client.handler.mapper.SmsPushMapper;
import com.kt.onnuripay.kafka.controller.DispatcherController;
import com.kt.onnuripay.kafka.controller.DispatcherControllerImpl;
import com.kt.onnuripay.kafka.dynamic.DynamicHandlerManager;
import com.kt.onnuripay.kafka.parser.KafkaMsgParser;

import lombok.Data;
import util.TestSeUpAbstract;

@Data
public class ControllerSetUp extends TestSeUpAbstract{
	
	public KafkaMsgParser mockParser = Mockito.mock(KafkaMsgParser.class);
	public DynamicHandlerManager mockHanlder = Mockito.mock(DynamicHandlerManager.class);
	public SmsPushMapper mockDb = Mockito.mock(SmsPushMapper.class);
	
	public DispatcherController controller = new DispatcherControllerImpl(mockParser,mockHanlder,mockDb);

	
	  


	@Override
	public void reset() {
		this.mockParser = Mockito.mock(KafkaMsgParser.class);
		this.mockHanlder = Mockito.mock(DynamicHandlerManager.class);
		this.mockDb = Mockito.mock(SmsPushMapper.class);		
	}
	
}
