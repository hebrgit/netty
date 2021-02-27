package com.netty.demo10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * @author Administrator
 * @class EnCodeFromLongHandler
 * @date 2021/2/25:20:51
 * @decs TODO
 */
public class LongToByteEncode extends MessageToByteEncoder<Long> {

//编码方法
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long o, ByteBuf byteBuf) throws Exception {
        System.out.println("LongToByteEncode==>encode()方法被调用");
        byteBuf.writeLong(o);
    }
}
