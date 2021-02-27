package com.netty.demo07;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName HttpNettyChannelInitializer
 * @Author hebo
 * @Date 2021/2/10 13:55
 **/
public class HttpNettyChannelInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {



        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        /**
         *  //向管道加入处理器
         *
         *  httpServerCodec 是netty提供的 http编解码器
         */
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        pipeline.addLast("MyHttpNettyChannelHandler",new HttpNettyChannelHandler());
    }
}
