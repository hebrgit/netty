package com.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author Administrator
 * @class MyMessageDecode
 * @date 2021/2/27:21:31
 * @decs TODO
 */
public class MyMessageDecode extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        System.out.println("MyMessageDecode ==> decode 被调用");
        //得到二进制字节码
        int i = byteBuf.readInt();
        byte [] context = new byte[i];
        byteBuf.readBytes(context);
        //将二进制字节码转成 MyMessageProtocol
        MyMessageProtocol protocol = new MyMessageProtocol();
        protocol.setCount(i);
        protocol.setContext(context);
        list.add(protocol);

        System.out.println("MyMessageDecode ==> decode 调用结束");
    }
}
