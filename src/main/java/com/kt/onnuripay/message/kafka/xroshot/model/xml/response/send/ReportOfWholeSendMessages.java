package com.kt.onnuripay.message.kafka.xroshot.model.xml.response.send;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.onnuripay.message.common.exception.XroshotRuntimeException;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.BaseXMLResponse;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class ReportOfWholeSendMessages extends BaseXMLResponse {
    
    public ReportOfWholeSendMessages(@JsonProperty("method") String methodName, @JsonProperty("Result")String result) {
        super(methodName, result);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean valid() throws XroshotRuntimeException {
        // TODO Auto-generated method stub
        return true;
    }

    
}
