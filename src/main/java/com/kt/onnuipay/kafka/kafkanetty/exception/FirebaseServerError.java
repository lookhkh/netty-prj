package com.kt.onnuipay.kafka.kafkanetty.exception;

import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

public class FirebaseServerError extends Throwable {

    public FirebaseServerError(ClientResponse res) {
        // TODO Auto-generated constructor stub
    }

    public FirebaseServerError(Mono<WebClientResponseException> createException) {
        // TODO Auto-generated constructor stub
    }
}
