package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.asynchttpclient.AsyncHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.MessageDecoderTo;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.RequestServeSyncTimeHandler;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.exception.AsyncExceptionHanlder;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.exception.FinalXroshotExceptionHanlder;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.impl.ParsingServerResponse;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.SmsSingleManager;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
import com.kt.onnuipay.kafka.kafkanetty.exception.XroshotRuntimeException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.Mas;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.ResourceInfo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.ServerTimeVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.SmsPushServerInfoVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.xml.XmlFrameDecoder;
import scala.Int;


/**
 * 
확인
 * 2. 서버 시간 요청
 * 3. SP 로그인(인증)요청
 *  3-1. 파일 업로드 경로 정보 요청
 *  3-2. 파일 업로드 서버 정보 요청
 *  3-3. 파일 업로드 및 완료 요청
 * 4. SMS&MMS 전송 요청
 * 5. DB 저장 요청
 * **/
@DisplayName("메시지 타입 및 종류에 따라 적절한 Handler를 생성 및 조합하는 Init 클래스 작성 테스트 - SMS")
public class SMSHandlerTestClass {

	AsyncHttpClient client = mock(AsyncHttpClient.class);
	XMLParser parser = mock(XMLParser.class);
	XroshotParameter param = mock(XroshotParameter.class);
	
	
	ParsingServerResponse parsing = mock(ParsingServerResponse.class);
	
	
	AsyncExceptionHanlder<Throwable, Void> e = mock(FinalXroshotExceptionHanlder.class);
	
	
	
	SendManager mng =  SmsSingleManager.builder()
						.client(client)
						.parser(parser)
						.param(param)
						.FinalXroshotExceptionHandler(null)
						.getSyncServerTime(null)
						.paringMsgServerInfo(parsing).build();
						

	XmlMapper realParser;
	XMLParser realParserMapper;
	

	SmsPushServerInfoVo info = SmsPushServerInfoVo.builder()
													.method(XMLConstant.MESSAGE_INFO_REQUEST)
													.result("0")
													.resource(ResourceInfo.builder()
															.category("MAS")
															.resourceId("MAS_xxx.xxx.xxx.243:8900")
															.address("xx.xxx.xxx.243")
															.port(80)
															.build()
															)
													.build();
	ServerTimeVo vo = ServerTimeVo.builder()
			.methodName(XMLConstant.RES_AUTH)
			.result(XMLConstant.OK)
			.time("20100127011151")
			.build();


	@BeforeEach
	public void init() {

			
			JacksonXmlModule module = new JacksonXmlModule();
			module.setDefaultUseWrapper(false);
			XmlMapper xmlMapper = new XmlMapper(module);
			xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
			this.realParser =  xmlMapper;
			this.realParserMapper  = new XMLParser(realParser);
	}
	
	@Test
	@DisplayName("1. 전송할 서버 위치 확인")
	public void test() throws JsonProcessingException, InterruptedException {
		
	
															
		String result = realParserMapper.parseToString(info);
		
		ParsingServerResponse r = new ParsingServerResponse(realParserMapper);
		CompletableFuture<SmsPushServerInfoVo> serverInfo =  r.execute(result);
		
		SmsPushServerInfoVo rrr = serverInfo.join();

		assertTrue(rrr.getResult().equals(info.getResult()));
		assertTrue(rrr.getResource().getAddress().equals(info.getResource().getAddress()));
		assertTrue(rrr.getResource().getCategory().equals(info.getResource().getCategory()));
		assertTrue(rrr.getResource().getPort() == info.getResource().getPort());

		
		SmsPushServerInfoVo mockVo = mock(SmsPushServerInfoVo.class);
		ParsingServerResponse r2 = new ParsingServerResponse(parser);
		when(parser.deserialzeFromJson(result, SmsPushServerInfoVo.class)).thenReturn(mockVo);
		when(mockVo.valid()).thenReturn(false);
		
		assertThrows(XroshotRuntimeException.class, ()->r2.execute(result));
		
	
	}
	

	@Test
	@DisplayName("2. 서버 시간 요청")
	public void test2() {
		String result = realParserMapper.parseToString(vo);

		Mas req = Mas.builder().method(XMLConstant.REQ_AUTH).serviceProviderID("temp").build();
		String serialziedReq = realParserMapper.parseToString(req);
			
		
		EmbeddedChannel ch = new EmbeddedChannel(
				new XmlFrameDecoder(Int.MaxValue())
				, new MessageDecoderTo<ServerTimeVo>(realParserMapper, ServerTimeVo.class) 
				, new RequestServeSyncTimeHandler(serialziedReq)
				);
		

		String convertedOutbound = ((ByteBuf)ch.readOutbound()).toString(CharsetUtil.UTF_8);
		assertEquals(convertedOutbound, serialziedReq);
				
		ByteBuf buf = Unpooled.copiedBuffer(result.getBytes(CharsetUtil.UTF_8));
//		
		ch.writeOneInbound(buf);
//
//		
		ServerTimeVo inboundResult = ch.readInbound();
		
		assertNotNull(inboundResult);
		assertTrue(inboundResult.getMethodName().equals(vo.getMethodName()));
		assertTrue(inboundResult.getTime().equals(vo.getTime()));

		
	}
	
	@Test
	@DisplayName("2.1 AuthTicket 생성")
	public void test2_1() {
		
		assertTrue(false);
		
	}
	

	@Test
	@DisplayName("3. SP 로그인(인증)요청")
	public void test3() {
		
		assertTrue(false);
		
	}
	
	
	@Test
	@DisplayName("4. SMS&MMS 전송 요청")
	public void test4() {
		
		assertTrue(false);
		
	}
	
	@Test
	@DisplayName("5. DB 저장 요청")
	public void test5() {
		
		assertTrue(false);
		
	}
	
	
}
