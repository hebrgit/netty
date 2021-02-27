package com.netty.demo08;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

/**
 * @ClassName NettyByteBuf01
 * @Author hebo
 * @Date 2021/2/23 14:28
 **/
public class NettyByteBuf01 {

    public static void main(String[] args) {

        //

        ByteBuf buffer = Unpooled.buffer(10);


        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.writeByte(i);
        }


        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.readByte();
        }
    }
}
