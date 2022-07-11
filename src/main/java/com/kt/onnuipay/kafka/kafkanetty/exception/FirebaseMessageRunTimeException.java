package com.kt.onnuipay.kafka.kafkanetty.exception;

import org.springframework.web.reactive.function.client.ClientResponse;

import lombok.Data;

@Data
public class FirebaseMessageRunTimeException extends RuntimeException {

    private  ClientResponse res;
    private String msg;
   
    public FirebaseMessageRunTimeException(String string, Throwable t) {
        super(string,t);
    }
    public FirebaseMessageRunTimeException(ClientResponse res2) {
        // TODO Auto-generated constructor stub
    }
    public FirebaseMessageRunTimeException(String string) {
        // TODO Auto-generated constructor stub
    }

}
