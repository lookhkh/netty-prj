package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.ExceptionHospitalHandler;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.RequestAuthTicketHandler;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.RequestServeSyncTimeHandler;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.SendSingleMessageHandler;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.codec.DefaultMessageToByteEncoder;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.codec.MessageDecoderTo;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.AuthInfoVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.ServerTimeVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import util.XroshotTestUtil;

@DisplayName("네티 xroshot 클라이언트 모듈 통합테스트")
public class NettyXroshotIntegeTest {

	XroshotParameter param = XroshotTestUtil.param;
	XmlMapper realParser;
	XMLParser realParserMapper;
	
	
	
	@BeforeEach
	public void init() {

			
			JacksonXmlModule module = new JacksonXmlModule();
			module.setDefaultUseWrapper(false);
			XmlMapper xmlMapper = new XmlMapper(module);
			xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
			
			this.realParser =  xmlMapper;
			this.realParserMapper  = new XMLParser(realParser);
	}
	
	
	/**
	 * 			네티 클라이언트               Xroshot
	 * 			
	 * 			서버 시간 요청		->>
	 * 							<--			서버 시간 응답
	 * 			
	 * 			로그인 요청  		->>	
	 * 							<---		로그인 응답 및 세션 생성
	 * 	
	 * 			메시지 전송		->>		
	 * 
	 * 
	 */
	@Test
	@DisplayName(" 네티 클라이언트 통합 테스트 => 로그인 ")
	public void test() {

		
		EmbeddedChannel ch = new EmbeddedChannel
		        (
				new LoggingHandler(LogLevel.DEBUG)
				, new MessageDecoderTo(realParserMapper) 
				, new DefaultMessageToByteEncoder(realParserMapper)
				, new RequestServeSyncTimeHandler(this.param)
				, new RequestAuthTicketHandler(this.param)
				, new ExceptionHospitalHandler()
				);
		
		ChannelPipeline pipeline = ch.pipeline();
		
	
	//로그인 과정 시작
		//채널 active 시, 서버 시간 정보를 요청
		String reqServerTime =  ((ByteBuf)ch.readOutbound()).toString(CharsetUtil.UTF_8);
		
		assertNotNull(reqServerTime);
		assertTrue(reqServerTime.contains(XMLConstant.REQ_AUTH));
		assertTrue(reqServerTime.contains("ServiceProviderID"));

		String serverTimeResponse =realParserMapper.parseToString(ServerTimeVo.builder()
				.methodName(XMLConstant.RES_SERVER_TIME)
				.result(XMLConstant.OK)
				.time("20100127011151")
				.build()); 
		
		ch.writeInbound(Unpooled.copiedBuffer(serverTimeResponse.getBytes(CharsetUtil.UTF_8)));
		
		assertNull(pipeline.get(RequestServeSyncTimeHandler.class));
		
		//채널 active 시, 서버 시간 정보를 요청
		
		

		//SP 로그인 요청
		String reqForLoginWithAuthTicket = ((ByteBuf)ch.readOutbound()).toString(CharsetUtil.UTF_8);		
		
		assertNotNull(reqForLoginWithAuthTicket);
		
		assertTrue(reqForLoginWithAuthTicket.contains("MAS method=\"req_regist\""));
		assertTrue(reqForLoginWithAuthTicket.contains("AuthTicket"));
		
		assertNull(pipeline.get(RequestAuthTicketHandler.class));

		//서버 로그인 정보
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
		
		String serverAuthInfoResponse = realParserMapper.parseToString(vo);
		ch.writeInbound(Unpooled.copiedBuffer(serverAuthInfoResponse.getBytes(CharsetUtil.UTF_8)));
		
		//SP 로그인 요청
	 //로그인 과정 완료

		//서버 에러 발생 시, 로그아웃 API 호출 및 채널 close
		

		assertAll(()->{
		   ChannelPipeline p = ch.pipeline();
		   
		   assertNotNull(p.get(LoggingHandler.class));
		   assertNotNull(p.get(MessageDecoderTo.class));
		   assertNotNull(p.get(DefaultMessageToByteEncoder.class));
		   assertNotNull(p.get(ExceptionHospitalHandler.class));
           
		});
		
		assertTrue(ch.isActive());
		
		
//에러 메시지 수신 시 처리 로직. 다른 테스트 케이스로 이전 예정
//		ch.writeInbound(Unpooled.copiedBuffer("test failed".getBytes()));
//		
//		String reqUnregistMessage = ((ByteBuf)ch.readOutbound()).toString(CharsetUtil.UTF_8);		
//
//		assertNotNull(reqUnregistMessage);
//		
//		assertTrue(reqUnregistMessage.contains("MAS method=\"req_unregist\""));
//		
//		assertFalse(ch.isActive());
		
		//서버 에러 발생 시, 로그아웃 API 호출 및 채널 close

	}
	
	
}
