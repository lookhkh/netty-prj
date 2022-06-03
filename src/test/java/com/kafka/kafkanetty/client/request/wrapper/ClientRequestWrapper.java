package com.kafka.kafkanetty.client.request.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientRequestWrapper<T> {

	private final T msg;
	
	
}
