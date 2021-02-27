package com.netty.demo10;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Administrator
 * @class MyServerHandler
 * @date 2021/2/25:20:45
 * @decs TODO
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("MyServerHandler==>channelRead0()方法被调用");
        System.out.println("读取到客户端发送的消息："+aLong);

        System.out.println("服务端发送消息："+10987L);
        channelHandlerContext.writeAndFlush(10987L);
    }



}
