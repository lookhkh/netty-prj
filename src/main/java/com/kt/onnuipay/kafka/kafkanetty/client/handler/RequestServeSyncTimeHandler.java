package com.kt.onnuipay.kafka.kafkanetty.client.handler;

import io.grpc.netty.shaded.io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class RequestServeSyncTimeHandler extends ChannelInboundHandlerAdapter {

	private final String json;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		ByteBuf b = ctx.channel().alloc().buffer();
		b.writeBytes(json.getBytes(CharsetUtil.UTF_8));
		
		ctx.writeAndFlush(b);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		log.info("{} is recived",msg);
		
		super.channelRead(ctx, msg);
		
		ctx.pipeline().forEach(System.out::println);
		
		

	}
}
