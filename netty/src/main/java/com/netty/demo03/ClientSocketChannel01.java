package com.netty.demo03;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @ClassName CLientSocketChannel01
 * @Author hebo
 * @Date 2021/2/2 8:50
 **/
public class ClientSocketChannel01 {


    public static void main(String[] args) throws Exception{


        //获取socketChannel
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);

        if (!socketChannel.connect(address)){
            System.out.println("等待链接");
            while (!socketChannel.finishConnect()){

            }
        }
        String str =  "hello world!!!";
        ByteBuffer wrap = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(wrap);
        System.in.read();
    }
}
