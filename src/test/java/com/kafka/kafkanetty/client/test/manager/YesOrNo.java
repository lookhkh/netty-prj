package com.kafka.kafkanetty.client.test.manager;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YesOrNo {

	private final boolean isSuccess;
	private final String id;
	private final Throwable t;
	
	public void check() {
		if(this.isSuccess) throw new IllegalStateException();
	}
}
