package com.netty.demo10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author Administrator
 * @class DecodeToLongHandler
 * @date 2021/2/25:20:42
 * @decs TODO
 */
public class ByteToLongDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        System.out.println("ByteToLongDecode==>decode()方法被调用");
        if (byteBuf.readableBytes()>=8){
            list.add(byteBuf.readLong());
        }
    }
}
