package com.kt.onnuipay.kafka.kafkanetty.exception;

import lombok.Data;

@Data
public class RunTimeExceptionWrapper extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	private String msg;
	private Object vo;
	private Throwable e;
	
	public RunTimeExceptionWrapper(String msg, Object vo, Throwable e) {
		this.msg = msg;
		this.vo = vo;
		this.e = e;
	}

	public RunTimeExceptionWrapper() {
		// TODO Auto-generated constructor stub
	}

}
