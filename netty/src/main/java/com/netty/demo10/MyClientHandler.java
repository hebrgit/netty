package com.netty.demo10;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @author Administrator
 * @class MyClientHandler
 * @date 2021/2/25:20:58
 * @decs TODO
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("MyClientHandler==>channelRead0()方法被调用 读取数据：");
        System.out.println(aLong);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler==>channelActive()方法被调用 发送数据");
        ctx.writeAndFlush(12355L);

//        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabdc", Charset.forName("utf-8")));
    }
}
