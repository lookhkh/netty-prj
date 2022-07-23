package com.kt.onnuripay.message.kafka.xroshot.client.handler;

public enum XroshotStatus {

    REQ_SERVER_TIME("REQ_SERVER_TIME"), REQ_AUTH("REQ_AUTH");
    
    private String status;

    private XroshotStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }
}
