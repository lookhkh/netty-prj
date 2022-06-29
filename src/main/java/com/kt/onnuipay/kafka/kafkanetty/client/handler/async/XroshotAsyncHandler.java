package com.kt.onnuipay.kafka.kafkanetty.client.handler.async;

import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.ListenableFuture;

import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XroshotAsyncHandler implements AsyncHandler<String> {

	private final ListenableFuture<String> f;
	
	public XroshotAsyncHandler(ListenableFuture<String> f) {
		this.f = f;
	}

	@Override
	public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
		// TODO Auto-generated method stub
		return State.CONTINUE;
	}

	@Override
	public State onHeadersReceived(HttpHeaders headers) throws Exception {
		// TODO Auto-generated method stub
		return State.CONTINUE;
	}

	@Override
	public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
		// TODO Auto-generated method stub
		return State.CONTINUE;
	}

	@Override
	public void onThrowable(Throwable t) {
		// TODO Auto-generated method stub
		t.printStackTrace();
	}

	@Override
	public String onCompleted() throws Exception {
		String result = f.get();
		log.info("result from request server info => {}",result);
		
		return result;
	}

	
}
