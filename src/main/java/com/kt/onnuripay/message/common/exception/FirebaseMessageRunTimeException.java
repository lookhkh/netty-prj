/*
 * KT OnnuriPay version 1.0
 *
 *  Copyright ⓒ 2022 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */
package com.kt.onnuripay.message.common.exception;

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
    }
    public FirebaseMessageRunTimeException(String string) {
    }

}
