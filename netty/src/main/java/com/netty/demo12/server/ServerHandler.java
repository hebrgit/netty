package com.netty.demo12.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Administrator
 * @class ServerHandler
 * @date 2021/3/1:20:09
 * @decs TODO
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static String PROTOCOL = "HelloServer#hello";
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常信息："+cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object s) throws Exception {
        System.out.println("ServerHandler ==> channelRead0()");
        //获取客户端发送的消息，并调用服务
        //客户端在调用服务端的api时，要定义一个协议
        if (s.toString().startsWith(PROTOCOL)){
            String substring = s.toString().substring(PROTOCOL.length() + 1);
            HelloServerImpl helloServer = new HelloServerImpl();
            String hello = helloServer.hello(substring);
            channelHandlerContext.writeAndFlush(hello);
        }else {
            System.out.println("无权限调用");
        }
    }
}
