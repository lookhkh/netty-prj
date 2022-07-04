package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.asynchttpclient.AsyncHttpClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.ExceptionHospitalHandler;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.RequestAuthTicketHandler;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.RequestServeSyncTimeHandler;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.SendSingleMessageHandler;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.exception.AsyncExceptionHanlder;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.exception.FinalXroshotExceptionHanlder;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.async.impl.ParsingServerResponse;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.codec.DefaultMessageToByteEncoder;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.codec.MessageDecoderTo;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.SmsSingleManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.util.NewXroshotAuth;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
import com.kt.onnuipay.kafka.kafkanetty.exception.XroshotRuntimeException;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.Mas;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.Message;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.AuthInfoVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.ResourceInfo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.ServerTimeVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.SmsPushServerInfoVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import datavo.msg.MessageWrapper;
import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


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
	
	
	XroshotParameter param = new XroshotParameter(
			"new1234", 
			"new1234", 
			"http://info.xroshot.com/catalogs/MAS/recommended/0", 
			"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", 
			"119.205.196.240", 
			"2", 
			"saupblkch1213!",
			"2", 
			"1.0.15"
			);
	
	
	ParsingServerResponse parsing = mock(ParsingServerResponse.class);

	AsyncExceptionHanlder<Throwable, Void> e = mock(FinalXroshotExceptionHanlder.class);
	
	SendManager mng =  SmsSingleManager.builder()
						.client(client)
						.parser(parser)
						.param(param)
						.FinalXroshotExceptionHandler(null)
						.prepareeAndStartClient(null)
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
			.methodName(XMLConstant.RES_SERVER_TIME)
			.result(XMLConstant.OK)
			.time("20100127011151")
			.build();

	EmbeddedChannel ch = null;
	
	@AfterEach
	public void cleanUp() throws InterruptedException {
		try {
		ch.close().sync();
		}catch(Exception e) {
			
		}
	}
	
	@BeforeEach
	public void init() {

			
			JacksonXmlModule module = new JacksonXmlModule();
			module.setDefaultUseWrapper(false);
			XmlMapper xmlMapper = new XmlMapper(module);
			xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
			this.realParser =  xmlMapper;
			this.realParserMapper  = new XMLParser(realParser);
	}
	
	public void test123123123() {
		
		
//		쓰레드풀에 100개의 쓰레드
//		50개
//		
//		Queue(100);
//		
//		-> 스레드풀 50;
//		-> 50개가 처리를
//			-> 50개의 쓰레드가 10초동안 block 
//			-> CPU 스케줄러가 16개 
//		-> 50개가 또 들어옴
//		-> 
//			-> 50개의 쓰레드가 10초동안 block
//			
//		10개의 요청
//			-> 10개오면 block
//		
			
		
	}
	
	@Test
	public void TEMP() {
		
		AuthInfoVo vo = AuthInfoVo.builder()
				.methodName(XMLConstant.RES_REGIST)
				.result(XMLConstant.OK)
				.sessionId("0")
//				.list(Arrays.asList(
//						new LimitedMsgPerSecond(1, "0")
//						,new LimitedMsgPerSecond(2, "10")
//						,new LimitedMsgPerSecond(3, "30")
//						,new LimitedMsgPerSecond(4, "100")))
//				.productList(Arrays.asList(
//						new ProductStatus(1, "Y")
//						,new ProductStatus(2, "Y")
//						,new ProductStatus(3, "Y")
//						,new ProductStatus(4, "N")))
//				.monthList(Arrays.asList(
//						new LimitedMsgPerMonth(1, "0")
//						,new LimitedMsgPerMonth(2, "10")
//						,new LimitedMsgPerMonth(3, "30")
//						,new LimitedMsgPerMonth(4, "100")))
				.build();

		
		String result = realParserMapper.parseToString(vo);
									
		String sample= "<MAS method=\"res_regist\"><Result>0</Result><SessionID>0</SessionID><SendLimitPerSecond msgType=1>0</ SendLimitPerSecond><SendLimitPerSecond msgType=2>100</ SendLimitPerSecond><SendLimitPerSecond msgType=3>100</ SendLimitPerSecond><SendLimitPerSecond msgType=4>100</ SendLimitPerSecond><ProductStatus msgType=1>Y</ProductStatus><ProductStatus msgType=2>Y</ProductStatus><ProductStatus msgType=3>Y</ProductStatus><ProductStatus msgType=4>Y</ProductStatus><SendLimitPerMonth msgType=1>1000</SendLimitPerMonth><SendLimitPerMonth msgType=2>1000</SendLimitPerMonth>\r\n<SendLimitPerMonth msgType=3>1000</SendLimitPerMonth>\r\n<SendLimitPerMonth msgType=4>1000</SendLimitPerMonth>\r\n</MAS>";
		String csample= "<MAS method=\"res_regist\"><Result>0</Result><SessionID>0</SessionID><SendLimitPerSecond msgType=1>0</ SendLimitPerSecond><SendLimitPerSecond msgType=2>100</ SendLimitPerSecond><SendLimitPerSecond msgType=3>100</ SendLimitPerSecond><SendLimitPerSecond msgType=4>100</ SendLimitPerSecond></MAS>";

		AuthInfoVo con = realParserMapper.deserialzeFromJson(result, AuthInfoVo.class);
		System.out.println(con);
	}	
	
	@Test
	@DisplayName("디코더 테스트")
	public void test0() {
		
		ch = new EmbeddedChannel(
				new LoggingHandler(LogLevel.DEBUG)
				, new MessageDecoderTo(realParserMapper) 
				, new DefaultMessageToByteEncoder(realParserMapper)
				, new ExceptionHospitalHandler()
				);
		
		ch.writeInbound(Unpooled.copiedBuffer("temp".getBytes(CharsetUtil.UTF_8)));
		ByteBuf f = ch.readOutbound();
		
		assertNotNull(f);
		assertTrue(f.toString(CharsetUtil.UTF_8).contains("<MAS method=\"req_unregist\"><Reason>0</Reason></MAS>"));

	}
	
	@Test
	@DisplayName("Error Hospital이 핸들러에서 발생할 수 있는 모든 에러를 처리한다.")
	public void error_Hospital() throws InterruptedException {

		ch = new EmbeddedChannel(
				new LoggingHandler(LogLevel.DEBUG)
				, new MessageDecoderTo(realParserMapper) 
				, new ExceptionHospitalHandler()
				);
		
		ch.writeOneInbound(Unpooled.copiedBuffer("random".getBytes())).sync();
		assertFalse(ch.isActive());
		assertFalse(ch.isOpen());
		

		

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
		Mockito.doThrow(new XroshotRuntimeException("error",mockVo)).when(mockVo).checkResultAndThrowIfInvalidData(mockVo);

		XroshotRuntimeException t= assertThrows(XroshotRuntimeException.class, ()->r2.execute(result));
		
		assertTrue(t.getR() instanceof SmsPushServerInfoVo);
	}
	

	@Test
	@DisplayName("2. 서버 시간 요청")
	public void test2() throws InterruptedException {
		String result = realParserMapper.parseToString(vo);

		Mas req = Mas.builder()
						.method(XMLConstant.REQ_AUTH)
						.serviceProviderID(this.param.getServiceProviderId())
						.build();
		
		String serialziedReq = realParserMapper.parseToString(req);
			
		
		ch = new EmbeddedChannel(
				new LoggingHandler(LogLevel.DEBUG)
				, new MessageDecoderTo(realParserMapper) 
				, new DefaultMessageToByteEncoder(realParserMapper)
				, new RequestServeSyncTimeHandler(this.param)
				);
		

		String convertedOutbound = ((ByteBuf)ch.readOutbound()).toString(CharsetUtil.UTF_8);
		assertEquals(convertedOutbound, serialziedReq);
				
		ByteBuf buf = Unpooled.copiedBuffer(result.getBytes(CharsetUtil.UTF_8));
		
		ch.writeOneInbound(buf).sync();

		
		ServerTimeVo inboundResult = ch.readInbound();
		
		assertNotNull(inboundResult);
		assertTrue(inboundResult.getMethodName().equals(vo.getMethodName()));
		assertTrue(inboundResult.getTime().equals(vo.getTime()));

		assertTrue(ch.pipeline().get(RequestServeSyncTimeHandler.class)==null);
		
		ch.close().sync();
	}
	
	
	@Test
	@DisplayName("3. AuthTicket 생성 및 SP 로그인 요청 발송")
	public void test2_1() throws InterruptedException {
		
		String result = realParserMapper.parseToString(vo);

			
		
		ch = new EmbeddedChannel(
				new LoggingHandler(LogLevel.DEBUG)
				, new MessageDecoderTo(realParserMapper) 
				, new DefaultMessageToByteEncoder(realParserMapper)
				, new RequestAuthTicketHandler(this.param)
				);
		
		
		ch.writeInbound(Unpooled.copiedBuffer(result.getBytes(CharsetUtil.UTF_8)));
		
		String outboundR = ((ByteBuf)ch.readOutbound()).toString(CharsetUtil.UTF_8);
		assertNotNull(outboundR);
		assertTrue(outboundR.contains("AuthTicket"));
		assertTrue(outboundR.contains("ServiceProviderID"));
		assertTrue(outboundR.contains("EndUserID"));
		assertTrue(outboundR.contains("req_regist"));
		
		assertNull(ch.pipeline().get(RequestAuthTicketHandler.class));
		

		
		
		ch.close().sync();
		
	}
	

	@Test
	@DisplayName("5_1. SMS 단건 전송")
	public void test5_1() throws InterruptedException {
		
		ch = new EmbeddedChannel(
				new LoggingHandler(LogLevel.DEBUG)
				, new MessageDecoderTo(realParserMapper) 
				, new DefaultMessageToByteEncoder(realParserMapper)
				, new SendSingleMessageHandler(null)
				);
		
		AuthInfoVo authInfo = AuthInfoVo.builder()
				.methodName(XMLConstant.RES_AUTH)
				.result(XMLConstant.OK)
				.sessionId("session")
				.build();

		String authInfoString = realParserMapper.parseToString(authInfo);

		ch.writeInbound(Unpooled.copiedBuffer(authInfoString.getBytes(CharsetUtil.UTF_8)));

		String f = ((ByteBuf)ch.readOutbound()).toString(CharsetUtil.UTF_8);
		
		assertNotNull(f);
		assertTrue(f.contains(XMLConstant.REQ_SEND_MESSAGE_SINGLE));
		
		ch.close().sync();
			
	}
	
	@Test
	@DisplayName("5_2. SMS 동보 전송")
	public void test5_2() {
		
		assertTrue(false);
		
	}
	
	
	@Test
	@DisplayName("5_3. SMS 대량 전송")
	public void test5_3() {
		
		assertTrue(false);
		
	}
	
	
	
	
	
	@Test
	@DisplayName("6. DB 저장 요청")
	public void test5() {
		
		assertTrue(false);
		
	}
	
	
}
