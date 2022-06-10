package com.kafka.kafkanetty.netty.client.test;

import static io.netty.handler.codec.http.HttpResponseStatus.ACCEPTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.kafkanetty.client.handler.NettyHttpRequestHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;


/*
 * TODO
 * API 연동 규격에 따라 헤더정보 변경 필요
 * 
 * */
@DisplayName("Client 용 Netty Handler 테스트")
public class ClientHandlerTest {

	private String host = "localhost";
	private String url = "localhost.com:8080";
	private HttpRequest initRequest;
	private FullHttpRequest requestWithpayLoad;
	ObjectMapper mapper = new ObjectMapper();
	TempVo voFromClient = new TempVo("조현일","IPHONE");

	
	private String serverResponseContent;
	
	
	@BeforeEach
	public void init() throws JsonProcessingException {
		
		serverResponseContent = mapper.writeValueAsString(new TempVo("서버반환이름","Android"));
		
		initRequest = new DefaultFullHttpRequest(
                 HttpVersion.HTTP_1_1, HttpMethod.GET, url, Unpooled.EMPTY_BUFFER);
		 
		initRequest.headers().set(HttpHeaderNames.HOST, host);
		initRequest.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		initRequest.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);
		
		
		requestWithpayLoad = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, url, Unpooled.buffer());
		requestWithpayLoad.headers().set(HttpHeaderNames.HOST, host);
		requestWithpayLoad.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		requestWithpayLoad.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);
		requestWithpayLoad.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);

		String jsonVal = mapper.writeValueAsString(voFromClient);
		ByteBuf byteBuf = Unpooled.copiedBuffer(jsonVal, StandardCharsets.UTF_8);

		
		requestWithpayLoad.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());		
		
		requestWithpayLoad.content().writeBytes(byteBuf);
		
	}
	
	
	@Test
	@DisplayName("초기 커넥션 생성 시 전달해야 할 헤더 정보 전송. 외부연동 API 규격에 따라 헤더정보 변경 필요")
	public void initRequestMsgWhenConnectionEstablishedWithServer() {
		EmbeddedChannel ch = new EmbeddedChannel(new HttpClientCodec(), new NettyHttpRequestHandler(initRequest,requestWithpayLoad));
		ByteBuf o = (ByteBuf)ch.readOutbound();
		
		byte[] content = new byte[o.readableBytes()];
		
		o.readBytes(content);
		
		String result = new String(content);
		
		String[] msg = result.split("\r\n");
		
		assertEquals("GET localhost.com:8080 HTTP/1.1", msg[0]);
		assertEquals("host: localhost", msg[1]);
		assertEquals("connection: keep-alive", msg[2]);
		assertEquals("accept-encoding: gzip", msg[3]);
		
		o.release();
		ch.finishAndReleaseAll();
	}
	
	@Test
	@DisplayName("핸들러로 HTTP FORMAT이 아닌 값이 오면 에러를 던지며 해당 채널을 닫는다.")
	public void ThrowErrorIfRecivedFormatIsNotHTTP() {
		EmbeddedChannel ch = new EmbeddedChannel(new NettyHttpRequestHandler(initRequest,requestWithpayLoad));
		ch.readOutbound();
		assertTrue(ch.isActive());
		ch.writeInbound(Unpooled.EMPTY_BUFFER);
		
		assertTrue(!ch.isActive());
	}
	
	@Test
	@DisplayName("서버로부터 Return을 받고 난 후 유저정보를 보낸다.")
	public void sendRequestDataWithPayload() throws JsonProcessingException {
		EmbeddedChannel ch = new EmbeddedChannel(new HttpClientCodec(), new NettyHttpRequestHandler(initRequest,requestWithpayLoad));
		ch.readOutbound();
		
		
		
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, ACCEPTED, Unpooled.copiedBuffer(serverResponseContent,CharsetUtil.UTF_8));
        ch.writeInbound(response);
        
        
        ch.readInbound();
        ByteBuf outbound = (ByteBuf)ch.readOutbound();
        
        
        assertNotNull(outbound);
        
        String result = outbound.toString(CharsetUtil.UTF_8);
        
        assertTrue(result.contains("connection: close"));
        assertTrue(result.contains(mapper.writeValueAsString(voFromClient)));
		
	}
	
	class TempVo{
		private String name;
		private String device;
		
		public TempVo(String name, String device) {
			this.name = name;
			this.device = device;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDevice() {
			return device;
		}
		public void setDevice(String device) {
			this.device = device;
		}
		
		
	}
	
}
