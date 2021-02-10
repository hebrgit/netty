package com.netty.demo06;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName NettyServerHandler
 * @Author hebo
 * @Date 2021/2/9 12:44
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println("客户端-"+socketAddress.toString()+":"+byteBuf.toString(CharsetUtil.UTF_8));


        ctx.channel().eventLoop().execute(()->{
            try {
                Thread.sleep(10*1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端1",CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ctx.channel().eventLoop().execute(()->{
            try {
                Thread.sleep(20*1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端2",CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("111");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {


        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
