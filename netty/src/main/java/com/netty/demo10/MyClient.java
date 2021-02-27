package com.netty.demo10;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Administrator
 * @class MyClient
 * @date 2021/2/25:20:47
 * @decs TODO
 */
public class MyClient {

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        try {

            bootstrap.group(nioEventLoopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    //添加编码器
                    pipeline.addLast(new LongToByteEncode());
                    pipeline.addLast(new ByteToLongDecode());
                    pipeline.addLast(new MyClientHandler());
                }
            });

            ChannelFuture future = bootstrap.connect("localhost", 7777).sync();
            future.channel().closeFuture().sync();
        }finally {
                nioEventLoopGroup.shutdownGracefully();
        }

    }
}
