package com.netty.demo06;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName NettyServer
 * @Author hebo
 * @Date 2021/2/9 12:25
 **/
public class NettyServer {

    public static void main(String[] args) {

       final EventLoopGroup boosGroup = new NioEventLoopGroup();
       final EventLoopGroup workerGroup = new NioEventLoopGroup();

       try {
           ServerBootstrap bootstrap = new ServerBootstrap()
                   .group(boosGroup, workerGroup)
                   .channel(NioServerSocketChannel.class)
                   .option(ChannelOption.SO_BACKLOG,128)
                   .childOption(ChannelOption.SO_KEEPALIVE,true)
                   .childHandler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           socketChannel.pipeline().addLast(new NettyServerHandler());
                       }
                   });

           //绑定并侦听6666端口
           ChannelFuture f = bootstrap.bind(6666).addListener((ChannelFutureListener)future -> {
             if (future.isSuccess()){
                 System.out.println("监听成功");
             }else {
                 System.out.println("监听失败");
             }
           }).sync();

           //监听断开连接事件
           f.channel().closeFuture().sync();



       }catch (Exception e){
           e.printStackTrace();
       }finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
       }

    }
}
