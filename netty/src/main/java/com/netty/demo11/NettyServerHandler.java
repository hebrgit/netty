package com.netty.demo11;

import com.netty.demo09.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;

/**
 * @ClassName NettyServerHandler
 * @Author hebo
 * @Date 2021/2/9 12:44
 **/
public class NettyServerHandler extends SimpleChannelInboundHandler<DataTypeInfo.MyMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataTypeInfo.MyMessage myMessage) throws Exception {

        DataTypeInfo.MyMessage.DataType dataType = myMessage.getDataType();

        if (dataType == DataTypeInfo.MyMessage.DataType.studentType){
            System.out.println("客户端消息："+myMessage.getStudent().getName());
        }else {
            System.out.println("客户端消息："+myMessage.getStudent().getName());
        }
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
