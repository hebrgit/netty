package com.netty.demo11;

import com.netty.demo09.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @ClassName NettyClientHandler
 * @Author hebo
 * @Date 2021/2/9 13:10
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //当通道就绪就会调用该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Random random  = new Random(3);
        DataTypeInfo.MyMessage message = null;
        if(random.nextInt() == 0){
          message =  DataTypeInfo.MyMessage.newBuilder().setDataType(DataTypeInfo.MyMessage.DataType.studentType_VALUE).setStudent(DataTypeInfo.Student.newBuilder().setId(3).setName("李四").build()).build();
        }else {
            message =  DataTypeInfo.MyMessage.newBuilder().setDataType(DataTypeInfo.MyMessage.DataType.workerType_VALUE).setWorker(DataTypeInfo.Worker.newBuilder().setName("李四").build()).build();
        }
        String s = message.toString();
        ctx.writeAndFlush(s);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

       ByteBuf buffer =  (ByteBuf) msg;
        System.out.println("服务器-"+ctx.channel().remoteAddress()+":"+buffer.toString(CharsetUtil.UTF_8));
    }


}
