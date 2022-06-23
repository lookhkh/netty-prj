package com.kt.onnuipay.callback;

import com.google.common.util.concurrent.FutureCallback;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;

import datavo.msg.MessageWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@AllArgsConstructor
public class FutureCallBackWrapper<V> implements FutureCallback<V> {

	private final MessageWrapper wrapper;
	private final TempMongodbTemplate db;
	
	@Override
	public void onSuccess(V result) {
		log.info("Message Success {}",result);
		
		db.insertDbHistory(ResultOfPush.builder()
							.metaData(wrapper.getMetaData())
							.isSuccess(true)
							.vo(wrapper)
							.build());
	}

	@Override
	public void onFailure(Throwable t) {
		log.warn("Message failed {}",t.getMessage(),t);
		
		db.insertDbHistory(ResultOfPush.builder()
				.metaData(wrapper.getMetaData())
				.isSuccess(false)
				.vo(wrapper)
				.reason(t)
				.build());
	}
}
