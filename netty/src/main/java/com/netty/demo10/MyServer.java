package com.netty.demo10;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Administrator
 * @class MyServer
 * @date 2021/2/25:20:38
 * @decs netty handler调用机制演示
 *  结论：1、不论解码器还是编码器，接收到的消息类型必须与待处理的消息类型一致，否则不会执行
 *      2、在解码器解码时，需要判断缓存区（bytebuf）的数据是否足够，否则接收到的结果会与期望值不一样
 */
public class MyServer {

    public static void main(String[] args) throws InterruptedException {


        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            //添加自定义的解码处理器
                            pipeline.addLast(new ByteToLongDecode());
                            //添加编码处理器
                            pipeline.addLast(new LongToByteEncode());

                            //添加自定义的处理器
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            ChannelFuture sync = serverBootstrap.bind(7777).sync();
            sync.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }



    }
}
