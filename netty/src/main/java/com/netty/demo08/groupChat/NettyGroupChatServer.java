package com.netty.demo08.groupChat;

import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author Administrator
 * @class NettyGroupChatServer
 * @date 2021/2/23:22:18
 * @decs TODO
 */
public class NettyGroupChatServer {

    private int port;

    public NettyGroupChatServer(int port){
        this.port = port;
    }

    public void run(){

        NioEventLoopGroup boos = new NioEventLoopGroup(1);
        NioEventLoopGroup works = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(boos,works)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            //获取管道 pipeline
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //向 pipeline 加入解码器
                            pipeline.addLast(new StringDecoder());
                            //向 pipeline 加入编码器
                            pipeline.addLast(new StringEncoder());
                            //向 pipeline 加入我们自己定义的处理器
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    });
            //绑定端口
            ChannelFuture f = serverBootstrap.bind(this.port).sync();
            //监听关闭
          f.channel().closeFuture().sync();
          System.out.println("netty 服务器启动");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boos.shutdownGracefully();
            works.shutdownGracefully();
        }


    }

    public static void main(String[] args) {
        NettyGroupChatServer nettyGroupChatServer = new NettyGroupChatServer(8888);
        nettyGroupChatServer.run();
    }
}
