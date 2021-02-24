package com.netty.demo08.webSocket;

import com.netty.demo08.groupChat.GroupChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author Administrator
 * @class MyServer
 * @date 2021/2/24:20:30
 * @decs 实现 http 长连接聊天
 */
public class MyServer {

    public static void main(String[] args) {

        NioEventLoopGroup boos = new NioEventLoopGroup(1);
        NioEventLoopGroup works = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(boos,works)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            //获取管道 pipeline
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //基于http 使用http解码器和编码器
                            pipeline.addLast(new HttpServerCodec());
                            //是以快方式写的 所以添加 ChunkedWriterHandler
                            pipeline.addLast(new ChunkedWriteHandler());
                            /**
                             * 1、http数据传输过程中是分段的，httpObjectAggregator 就是可以将多个段聚合起来，这就是为什么当浏览器发送大量数据时，就会出现多次http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            /**
                             * webSocket 传送数据就是以帧（frame）形式传递的
                             * WebSocketServerProtocolHandler 核心功能是将http协议升级为ws协议，保持长连接
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            //自定义handler 处理业务逻辑
                            pipeline.addLast(new MyTextWebSocketFrameHandler());
                        }
                    });
            //绑定端口
            ChannelFuture f = serverBootstrap.bind(7000).sync();
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
}
