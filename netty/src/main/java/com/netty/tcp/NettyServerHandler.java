package com.netty.tcp;

import com.netty.demo11.DataTypeInfo;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @ClassName NettyServerHandler
 * @Author hebo
 * @Date 2021/2/9 12:44
 **/
public class NettyServerHandler extends SimpleChannelInboundHandler<MyMessageProtocol> {

    private int num;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyMessageProtocol myMessage) throws Exception {
        System.out.println("NettyServerHandler ==> channelRead0 被调用");
        int count = myMessage.getCount();
        byte[] context = myMessage.getContext();
        System.out.println("服务端接收到的信息如下：");
        System.out.println("长度："+count);
        System.out.println("内容："+new String(context, Charset.forName("utf-8")));
        System.out.println("服务器接收到的消息包数量："+(++num));


        channelHandlerContext.writeAndFlush(myMessage);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {



    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
