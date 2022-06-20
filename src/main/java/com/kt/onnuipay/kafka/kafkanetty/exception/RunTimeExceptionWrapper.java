package com.kt.onnuipay.kafka.kafkanetty.exception;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

import lombok.Data;

@Data
public class RunTimeExceptionWrapper extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	private String msg;
	private MsgFromKafkaVo vo;
	private Throwable e;
	
	public RunTimeExceptionWrapper(String msg, MsgFromKafkaVo vo, Throwable e) {
		this.msg = msg;
		this.vo = vo;
		this.e = e;
	}

	public RunTimeExceptionWrapper() {
		// TODO Auto-generated constructor stub
	}

}
