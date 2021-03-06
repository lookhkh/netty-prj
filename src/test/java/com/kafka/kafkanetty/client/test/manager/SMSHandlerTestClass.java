package com.kafka.kafkanetty.client.test.manager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.kafka.parser.XMLParser;
import com.kt.onnuripay.message.kafka.xroshot.client.channelmanager.XroshotChannelManager;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.ExceptionHospitalHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.RequestAuthTicketHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.RequestServeSyncTimeHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.SendSingleMessageHandler;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.codec.DefaultMessageToByteEncoder;
import com.kt.onnuripay.message.kafka.xroshot.client.handler.codec.MessageDecoderTo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.Mas;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.XMLConstant;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.auth.AuthInfoVo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.send.MessageSendRequestResult;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.send.ReportOfWholeSendMessages;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.serverInfo.ResourceInfo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.serverInfo.SmsPushServerInfoVo;
import com.kt.onnuripay.message.kafka.xroshot.model.xml.response.serverTime.ServerTimeVo;

import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import util.XroshotTestUtil;


/**
 * 
??????
 * 2. ?????? ?????? ??????
 * 3. SP ?????????(??????)??????
 *  3-1. ?????? ????????? ?????? ?????? ??????
 *  3-2. ?????? ????????? ?????? ?????? ??????
 *  3-3. ?????? ????????? ??? ?????? ??????
 * 4. SMS&MMS ?????? ??????
 * 5. DB ?????? ??????
 * **/
@DisplayName("????????? ?????? ??? ????????? ?????? ????????? Handler??? ?????? ??? ???????????? Init ????????? ?????? ????????? - SMS")
public class SMSHandlerTestClass {

	XMLParser parser = mock(XMLParser.class);
	
	XroshotParameter param = XroshotTestUtil.param;

	XMLParser realParserMapper = XroshotTestUtil.getParser();

    XroshotChannelManager manager = Mockito.mock(XroshotChannelManager.class);


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
  @DisplayName("XML??? ???????????? ?????? ???, <Mas>??? ???????????? ???????????? ???????????? ?????? ???????????? ???????????????")
  public void t2() {
      
      String str = "<MAS method=\"res_ping\">\r\n"
                + "  <Result>0</Result>\r\n"
                + "</MAS>"+"<MAS method=\"res_regist\">\r\n"
                        + "  <Result>0</Result>\r\n"
                        + "  <SessionID>0</SessionID>\r\n"
                        + "  <SendLimitPerSecond msgType=\"1\">10</SendLimitPerSecond>\r\n"
                        + "  <SendLimitPerSecond msgType=\"4\">10</SendLimitPerSecond>\r\n"
                        + "  <ProductStatus msgType=\"1\">Y</ProductStatus>\r\n"
                        + "  <ProductStatus msgType=\"2\">N</ProductStatus>\r\n"
                        + "  <ProductStatus msgType=\"3\">N</ProductStatus>\r\n"
                        + "  <ProductStatus msgType=\"4\">Y</ProductStatus>\r\n"
                        + "  <SendLimitPerMonth msgType=\"1\">10000</SendLimitPerMonth>\r\n"
                        + "  <SendLimitPerMonth msgType=\"4\">10000</SendLimitPerMonth>\r\n"
                        + "</MAS>\r\n";
      
      ch = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG)
                , new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,Unpooled.copiedBuffer("</MAS>".getBytes(CharsetUtil.UTF_8)))
                , new MessageDecoderTo(realParserMapper) 
                , new DefaultMessageToByteEncoder(realParserMapper)
                , new ExceptionHospitalHandler()
                );
      
      ch.writeInbound(Unpooled.copiedBuffer(str.getBytes(CharsetUtil.UTF_8)));
      
          String ping =  ch.readInbound().toString();
          String auth = ch.readInbound().toString();
          
          assertTrue(ping.contains(XMLConstant.RES_PING));
          assertTrue(auth.contains(XMLConstant.RES_REGIST));
          

          String resultMessages = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
                  + "<MAS method=\"res_send_message\">\r\n"
                  + "<Result>0</Result>\r\n"
                  + "<Time>20100126160941</Time>\r\n"
                  + "<CustomMessageID>11145</CustomMessageID>\r\n"
                  + "<SequenceNumber>1</SequenceNumber>\r\n"
                  + "<JobID>132858647</JobID>\r\n"
                  + "<GroupID>132858646</GroupID>\r\n"
                  + "</MAS>\r\n"
                  + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
                  + "<MAS method=\"res_send_message\">\r\n"
                  + "<Result>0</Result> \r\n"
                  + "<Time>20100126160941</Time> \r\n"
                  + "<CustomMessageID>11145</CustomMessageID>\r\n"
                  + "<SequenceNumber>2</SequenceNumber> \r\n"
                  + "<JobID>132858648</JobID> \r\n"
                  + "<GroupID>132858646</GroupID> \r\n"
                  + "</MAS>\r\n"
                  + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
                  + "<MAS method=\"res_send_message_all\">\r\n"
                  + " <Result>0</Result>\r\n"
                  + " <Time>20100126160941</Time>\r\n"
                  + " <CustomMessageID>11145</CustomMessageID>\r\n"
                  + " <Count>2</Count> JobID \r\n"
                  + " <GroupID>132858646</GroupID>\r\n"
                  + "<JobID seqNo=\"1\">132858647</JobID>\r\n"
                  + "<JobID seqNo=\"2\">132858648</JobID>\r\n"
                  + "</MAS>";
          
          ch.writeInbound(Unpooled.copiedBuffer(resultMessages.getBytes(CharsetUtil.UTF_8)));
          
         assertDoesNotThrow(()->{
             MessageSendRequestResult message1 =  ch.readInbound();
             MessageSendRequestResult message2 =  ch.readInbound();
             ReportOfWholeSendMessages message3 =  ch.readInbound();          
         });
  }
	

	@Test
    @DisplayName("????????? ?????????")
    public void test0() {
        
        ch = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG)
                , new MessageDecoderTo(realParserMapper) 
                , new DefaultMessageToByteEncoder(realParserMapper)
                , new ExceptionHospitalHandler()
                );
        
        ch.attr(XroshotChannelManager.KEY).set(XroshotChannelManager.REQ_AUTH);
        
        ch.writeInbound(Unpooled.copiedBuffer("temp".getBytes(CharsetUtil.UTF_8)));
        ByteBuf f = ch.readOutbound();
        
        assertNotNull(f);
        assertTrue(f.toString(CharsetUtil.UTF_8).contains("<MAS method=\"req_unregist\"><Reason>0</Reason></MAS>"));

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
	@DisplayName("?????? ??????????????? validation??? ????????? ??? ????????? ?????????.")
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
	@DisplayName("Error Hospital??? ??????????????? ????????? ??? ?????? ?????? ????????? ????????????.")
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
	@DisplayName("2. ?????? ?????? ??????")
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
	@DisplayName("3. AuthTicket ?????? ??? SP ????????? ?????? ??????")
	public void test2_1() throws InterruptedException {
		
		String result = realParserMapper.parseToString(vo);

		System.out.println(result);
		
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
		
		AuthInfoVo au = AuthInfoVo.builder().methodName(XMLConstant.RES_REGIST).result(XMLConstant.OK).sessionId("session").build();
		
		ch.writeInbound(Unpooled.copiedBuffer(realParserMapper.parseToString(au).getBytes(CharsetUtil.UTF_8)));
		
		assertNull(ch.pipeline().get(RequestAuthTicketHandler.class));
		

		
		
		ch.close().sync();
		
	}
	

	@Test
	@DisplayName("5_1. SMS ?????? ??????")
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
	@DisplayName("5_2. SMS ?????? ??????")
	public void test5_2() {
		
		assertTrue(false);
		
	}
	
	
	@Test
	@DisplayName("5_3. SMS ?????? ??????")
	public void test5_3() {
		
		assertTrue(false);
		
	}
	
	
	
	
	
	@Test
	@DisplayName("6. DB ?????? ??????")
	public void test5() {
		
		assertTrue(false);
		
	}
	
	
}
