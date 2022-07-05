package com.kt.onnuipay.kafka.kafkanetty.client.handler;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.Mas;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;

import datavo.msg.MessageWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@Slf4j
public class SendSingleMessageHandler extends ChannelInboundHandlerAdapter {

	private final MessageWrapper wrapper;
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		if(log.isDebugEnabled()) log.debug("{} handler removed ",ctx.channel());
		super.handlerRemoved(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("SendSingleHanlder Received AuthInfo {}", msg);
		
		
		
		Mas mas = createMasDependingOnMessage(wrapper);
		
		log.info("메시지 생성 => {}",mas);
		
		ctx.writeAndFlush(mas);
		
		
		
	}

	private Mas createMasDependingOnMessage(MessageWrapper msgs) {

		/**
		 * TODO 메시지 타입에 따라서 Mas 빌드하기 220704 조현일
		 * 
		 * **/
		
		return Mas.builder().method(XMLConstant.REQ_SEND_MESSAGE_SINGLE).build();
	}
}
