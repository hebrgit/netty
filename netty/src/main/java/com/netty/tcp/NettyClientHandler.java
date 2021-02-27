package com.netty.tcp;

import com.netty.demo11.DataTypeInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * @ClassName NettyClientHandler
 * @Author hebo
 * @Date 2021/2/9 13:10
 **/
public class NettyClientHandler extends SimpleChannelInboundHandler<MyMessageProtocol> {

    private int num;
    //当通道就绪就会调用该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("NettyClientHandler ==> channelActive 被调用");
        String str = "你好，世界！";

        for (int i = 0; i < 5; i++) {
            MyMessageProtocol protocol = new MyMessageProtocol();
            protocol.setContext(str.getBytes(Charset.forName("utf-8")));
            protocol.setCount(str.getBytes(Charset.forName("utf-8")).length);
            ctx.writeAndFlush(protocol);
        }
        System.out.println("NettyClientHandler ==> channelActive 调用结束");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MyMessageProtocol msg) throws Exception {
        System.out.println("NettyClientHandler ==> channelRead0 被调用");

        System.out.println("客户端回复消息如下：");
        System.out.println("长度："+msg.getCount());
        System.out.println("内容："+new String(msg.getContext(),Charset.forName("utf-8")));
        System.out.println("NettyClientHandler ==> channelRead0 调用结束");

    }


}
