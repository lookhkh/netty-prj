package com.kt.onnuipay.kafka.kafkanetty.client.handler.codec;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kt.onnuipay.kafka.kafkanetty.exception.XroshotRuntimeException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.AuthInfoVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.BaseXMLResponse;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.ServerTimeVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.SmsPushServerInfoVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class MessageDecoderTo extends MessageToMessageDecoder<ByteBuf> {

	private final XMLParser parser;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		
		
		
			String msgJson = msg.toString(CharsetUtil.UTF_8);
			
			Class<?> clazz = getClassFromMethod(extractMethodFromServer(msgJson));
			
			String serializedMessage = msg.toString(CharsetUtil.UTF_8);
			
			BaseXMLResponse baseVo = validationResponse(clazz, serializedMessage);
			
			out.add(baseVo);
			
			if(log.isDebugEnabled()) log.debug("Message decoded from {} to {} with class {}",msg, msgJson, clazz);
		
		}

	private BaseXMLResponse validationResponse(Class<?> clazz, String serializedMessage) {
		Object base = parser.deserialzeFromJson(serializedMessage, clazz);
		
		if(!(base instanceof BaseXMLResponse)) throw new XroshotRuntimeException("parsing error happend at decoder. message info => "+serializedMessage, base);
		
		BaseXMLResponse baseVo = (BaseXMLResponse)base;
		
		baseVo.checkResultAndThrowIfInvalidData(baseVo);
		
		return baseVo;
	}

	private Class<?> getClassFromMethod(String extractMethodFromServer) {
		if(extractMethodFromServer.equals(XMLConstant.RES_SERVER_TIME)) return ServerTimeVo.class;
		if(extractMethodFromServer.equals(XMLConstant.RES_REGIST)) return AuthInfoVo.class;
		if(extractMethodFromServer.equals(XMLConstant.MESSAGE_INFO_REQUEST)) return SmsPushServerInfoVo.class;
		
		return null;
	}

	private String extractMethodFromServer(String msgJson) {
		String pipeStr = msgJson.substring(msgJson.indexOf("method")+"method=\"".length());
		String method = pipeStr.substring(0,pipeStr.indexOf("\""));
	
		if(log.isDebugEnabled()) log.debug("message parsing for factory, msg => {}, result => {}",msgJson, method);

		
		return method;
	}
	


}
