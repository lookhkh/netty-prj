package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.common.exception.XroshotRuntimeException;
import com.kt.onnuripay.message.kafka.parser.XMLParser;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.ExceptionHospitalHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.RequestAuthTicketHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.RequestServeSyncTimeHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.SendSingleMessageHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.codec.DefaultMessageToByteEncoder;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.codec.MessageDecoderTo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.AuthInfoVo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.ResourceInfo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.ServerTimeVo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.SmsPushServerInfoVo;

import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import util.XroshotTestUtil;


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

	XMLParser parser = mock(XMLParser.class);
	
	XroshotParameter param = XroshotTestUtil.param;

	XMLParser realParserMapper = XroshotTestUtil.getParser();

	

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
	@DisplayName("서버 리스폰스의 validation이 실패할 시 에러를 던진다.")
	public void testError() {
		
		String errorCode = "some Error code";
		
		ch = new EmbeddedChannel(
				new LoggingHandler(LogLevel.DEBUG)
				, new MessageDecoderTo(realParserMapper) 

				);
		
		String serverTimeResponse =realParserMapper.parseToString(ServerTimeVo.builder()
				.methodName(XMLConstant.RES_SERVER_TIME)
				.result(errorCode)
				.time(null)
				.build()); 
		
		AuthInfoVo vo = AuthInfoVo.builder()
				.methodName(XMLConstant.RES_REGIST)
				.result(errorCode)
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
		
		String serverAuthInfoResponse = realParserMapper.parseToString(vo);
			
		assertThrows(DecoderException.class , ()-> ch.writeInbound(
																Unpooled.copiedBuffer(serverTimeResponse.getBytes(CharsetUtil.UTF_8)))
				);
		
		assertThrows(DecoderException.class , ()-> ch.writeInbound(
				Unpooled.copiedBuffer(serverAuthInfoResponse.getBytes(CharsetUtil.UTF_8)))
);
		
		
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
				.methodName(XMLConstant.RES_REGIST)
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
