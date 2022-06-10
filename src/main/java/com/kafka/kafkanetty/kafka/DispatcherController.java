package com.kafka.kafkanetty.kafka;

import com.kafka.kafkanetty.kafka.model.ResultOfPush;

public interface DispatcherController {

	
	public ResultOfPush route(String msg);

}
