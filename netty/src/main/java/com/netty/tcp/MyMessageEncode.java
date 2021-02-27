package com.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Administrator
 * @class MyMessageEncode
 * @date 2021/2/27:21:21
 * @decs TODO
 */
public class MyMessageEncode extends MessageToByteEncoder<MyMessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyMessageProtocol protocol, ByteBuf byteBuf) throws Exception {

        System.out.println("MyMessageEncode ==> encode 被调用");

        int count = protocol.getCount();
        byteBuf.writeInt(count);
        byteBuf.writeBytes(protocol.getContext());

        System.out.println("MyMessageEncode ==> encode 调用结束");
    }
}
