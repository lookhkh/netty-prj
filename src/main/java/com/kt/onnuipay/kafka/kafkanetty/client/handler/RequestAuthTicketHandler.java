package com.kt.onnuipay.kafka.kafkanetty.client.handler;


import org.asynchttpclient.netty.SimpleChannelFutureListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.kt.onnuipay.kafka.kafkanetty.client.handler.util.NewXroshotAuth;
import com.kt.onnuipay.kafka.kafkanetty.config.vo.XroshotParameter;
import com.kt.onnuipay.kafka.kafkanetty.exception.RunTimeExceptionWrapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.Mas;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.XMLConstant;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response.ServerTimeVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.XMLParser;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service(value = "auth_ticket_handler")
@Slf4j
@AllArgsConstructor
@Sharable
public class RequestAuthTicketHandler extends ChannelInboundHandlerAdapter {
	
	private final XroshotParameter param;

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		if(log.isDebugEnabled()) log.debug("{} RequestAuthTicketHandler removed",ctx.channel());
		super.handlerRemoved(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("received Msg from previous handler {}",msg);
		
		ServerTimeVo vo = (ServerTimeVo)msg;
		
		Mas req = Mas.builder()
					.method(XMLConstant.REQ_REGIST)
					.serviceProviderID(this.param.getServiceProviderId())
					.endUserID(param.getEndUserId())
					.authTicket(createAuthTicket(vo,param))
					.authKey(param.getAuthKey())
					.version(param.getVersion())
					.build();
		
		log.info("completed xml req -> {}",req.toString());
					
		ctx.writeAndFlush(req).addListener(new DefaultChannelHandlerListener(this));
		
		
		
	}

	private String createAuthTicket(ServerTimeVo vo, XroshotParameter param) {
		
		String tempPublicKey =param.getCertFile().substring(param.getIndexBegin(), param.getIndexEnd()) ;
		String tempAuthTicket = param.getServiceProviderId() + "|" + param.getServiceProviderPw() + "|" + param.getServiceProviderId() + "|" + vo.getTime() + "|" + param.getOneTimeSecretKey();

		try {
			return NewXroshotAuth.encrypto2(tempAuthTicket.getBytes(), tempPublicKey.getBytes());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RunTimeExceptionWrapper("Error Happend during generating authticket", null, e);
		}
	}
}
