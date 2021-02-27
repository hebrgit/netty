package com.netty.demo08;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @author Administrator
 * @class ByteBuf01
 * @date 2021/2/23:21:16
 * @decs TODO
 */
public class ByteBuf01 {


    public static void main(String[] args) {

        //创建一个ByteBuf

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", Charset.forName("utf-8"));

        //判断是否有数据
        if(byteBuf.hasArray()){

            byte[] array = byteBuf.array();

            System.out.println(new String(array,Charset.forName("utf-8")));

            //偏移量
            System.out.println(byteBuf.arrayOffset());

            //读取量
            System.out.println(byteBuf.readerIndex());
            //写入量
            System.out.println(byteBuf.writerIndex());
            //总容量
            System.out.println(byteBuf.capacity());
            //可读字节数
            System.out.println(byteBuf.readableBytes());
            //设置读取范围
            System.out.println(byteBuf.getCharSequence(0,4,Charset.forName("utf-8")));
        }
    }
}
