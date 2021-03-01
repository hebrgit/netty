package com.netty.demo12.client;

import com.netty.demo12.server.NettyServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 * @class NettyClient
 * @date 2021/3/1:20:30
 * @decs TODO
 */
public class NettyClient {

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static ClientHandler clientHandler;

//    public  Object getBean(final Class<?> c,final String protocol){
//
//        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{c}, ((proxy, method, args) -> {
//
//            if (clientHandler == null) {
//
//                initNettyClient();
//            }
//            clientHandler.setPara(protocol + args[0]);
//            return executorService.submit(clientHandler).get();
//        }));
//    }

    public static void initNettyClient(){
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        clientHandler = new ClientHandler();
        clientHandler.setPara("111");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();

                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new ClientHandler());
                    }
                });

        try {
            bootstrap.connect("localhost", 9000).sync().addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("监听成功");
                }else{
                    System.out.println("监听失败");
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        initNettyClient();
        executorService.submit(clientHandler);
    }


}
