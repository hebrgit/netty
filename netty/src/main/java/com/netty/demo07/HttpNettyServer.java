package com.netty.demo07;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName HttpNettyServer
 * @Author hebo
 * @Date 2021/2/10 13:51
 **/
public class HttpNettyServer {

    public static void main(String[] args) {
        NioEventLoopGroup boos = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();


        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(boos,worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new HttpNettyChannelInitializer());


            ChannelFuture f = serverBootstrap.bind(8080).addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("监听成功");
                }else{
                    System.out.println("监听失败");
                }
            }).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
