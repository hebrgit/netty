package com.netty.demo06;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.util.CharsetUtil;

import java.nio.Buffer;

/**
 * @ClassName NettyClientHandler
 * @Author hebo
 * @Date 2021/2/9 13:10
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,服务器", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

       ByteBuf buffer =  (ByteBuf) msg;
        System.out.println("服务器-"+ctx.channel().remoteAddress()+":"+buffer.toString(CharsetUtil.UTF_8));
    }
}
