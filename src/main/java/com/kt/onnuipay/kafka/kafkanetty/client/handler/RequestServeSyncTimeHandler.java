package com.kt.onnuipay.kafka.kafkanetty.client.handler;

import org.springframework.stereotype.Service;

import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.Mas;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("request_server_time_handler")
@Sharable
@Slf4j
@AllArgsConstructor
public class RequestServeSyncTimeHandler extends ChannelInboundHandlerAdapter {

	private final XroshotParameter param;
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		if(log.isDebugEnabled()) log.debug("{} handler removed ",ctx.channel());
		super.handlerRemoved(ctx);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		Mas req = Mas.builder()
				.method(XMLConstant.REQ_AUTH)
				.serviceProviderID(param.getServiceProviderId())
				.build();
		
		
		ctx.writeAndFlush(req).addListener(new DefaultChannelHandlerListener(this));
	}
	
	
}
