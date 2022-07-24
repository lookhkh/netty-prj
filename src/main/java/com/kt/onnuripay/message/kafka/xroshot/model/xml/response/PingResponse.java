package com.kt.onnuripay.message.kafka.xroshot.model.xml.response;

import com.kt.onnuripay.message.common.exception.XroshotRuntimeException;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PingResponse extends BaseXMLResponse{

    
    public PingResponse(String methodName, String result) {
        super(methodName, result);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean valid() throws XroshotRuntimeException {
        // TODO Auto-generated method stub
        return this.getResult().equals(XMLConstant.OK);
    }
}
