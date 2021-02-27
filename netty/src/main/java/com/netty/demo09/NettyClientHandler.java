package com.netty.demo09;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @ClassName NettyClientHandler
 * @Author hebo
 * @Date 2021/2/9 13:10
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //当通道就绪就会调用该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(3).setName("张三").build();

        ctx.writeAndFlush(student);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

       ByteBuf buffer =  (ByteBuf) msg;
        System.out.println("服务器-"+ctx.channel().remoteAddress()+":"+buffer.toString(CharsetUtil.UTF_8));
    }


}
