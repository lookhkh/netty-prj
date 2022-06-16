package com.kt.onnuipay.kafka.kafkanetty.exception;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.Data;

@Data
public class DataBodyInvalidException extends IllegalArgumentException {

	/**
	 * 123123123f
	 */
	private static final long serialVersionUID = 1L;
	private final String errorMsg;
	private final MsgFromKafkaVo errorVo;
	
	public DataBodyInvalidException(String errorMsg, MsgFromKafkaVo msgFromKafkaVo) {
		super(errorMsg);
		this.errorMsg = errorMsg;
		this.errorVo = msgFromKafkaVo;
	}

}
