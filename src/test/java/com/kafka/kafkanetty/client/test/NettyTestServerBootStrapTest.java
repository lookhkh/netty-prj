package com.kafka.kafkanetty.client.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyTestServerBootStrapTest {
    private int port;
    
    private EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
    
    public NettyTestServerBootStrapTest(int port) {
        this.port = port;
    }
    
    public void run() throws Exception {
    	System.out.println("STRART");
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, bossGroup)
             .channel(NioServerSocketChannel.class) // (3)
             .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new ChannelInboundHandlerAdapter () {
                    	 @Override
                    	    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
                    	        // Discard the received data silently.
                    	        ((ByteBuf) msg).release(); // (3)
                    	    }

                    	    @Override
                    	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
                    	        // Close the connection when an exception is raised.
                    	        cause.printStackTrace();
                    	        ctx.close();
                    	    }
                     });
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
    
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)
    
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }

	public void stop() {
		// TODO Auto-generated method stub
          bossGroup.shutdownNow();
          }
    

}
