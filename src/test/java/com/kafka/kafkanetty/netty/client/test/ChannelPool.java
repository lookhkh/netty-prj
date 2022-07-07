package com.kafka.kafkanetty.netty.client.test;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolHandler;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

public class ChannelPool {

    private final static int MAX_CONNECTION = 4;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        final Bootstrap clientBootstrap = new Bootstrap();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080);

        clientBootstrap.group(group).channel(NioSocketChannel.class);

        ChannelPoolMap<InetSocketAddress, FixedChannelPool> channelPoolMap = new AbstractChannelPoolMap<InetSocketAddress, FixedChannelPool>() {
            @Override
            protected FixedChannelPool newPool(InetSocketAddress key) {
                return new FixedChannelPool(clientBootstrap.remoteAddress(key), new AbstractChannelPoolHandler() {
                    @Override
                    public void channelCreated(Channel ch) throws Exception {
                        System.out.println(ch);
                        ch.pipeline().addLast(new LoggingHandler());
                    }
                }, MAX_CONNECTION);
            }
        };

        final FixedChannelPool channelPool = channelPoolMap.get(address);
        
        Future<Channel> f = channelPool.acquire();
        
        f.addListener((FutureListener<Channel>) future -> {
            if (future.isSuccess()) {
                System.out.println("Acquire successful");
                Channel ch = future.getNow();
                
                System.out.println(ch.pipeline()+" result ===");
                
                
                channelPool.release(ch);
                System.out.println("Release channel");
            } else {

            }
        });

    }
}
