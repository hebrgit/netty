package com.netty.demo08.groupChat;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Scanner;

/**
 * @author Administrator
 * @class GroupChatClient
 * @date 2021/2/23:22:44
 * @decs TODO
 */
public class GroupChatClient {

    private String host;
    private int port;

    public GroupChatClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {

        NioEventLoopGroup works = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(works)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //获取管道 pipeline
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //向 pipeline 加入解码器
                            pipeline.addLast(new StringDecoder());
                            //向 pipeline 加入编码器
                            pipeline.addLast(new StringEncoder());
                            //向 pipeline 加入我们自己定义的处理器
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });
            ChannelFuture f = bootstrap.connect(host, port).sync();
            Channel channel = f.channel();
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLine()){
                String s = scanner.nextLine();
                channel.writeAndFlush(s+"\r\n");
            }
        }finally {
            works.shutdownGracefully();
        }



    }

    public static void main(String[] args) throws InterruptedException {
        GroupChatClient localhost = new GroupChatClient("localhost", 8888);
        localhost.run();
    }
}
