package com.netty.demo05;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Administrator
 * @class NewClient
 * @date 2021/2/6:23:33
 * @decs TODO
 */
public class NewIoClient {

    public static void main(String[] args) throws IOException {

        SocketChannel open = SocketChannel.open();

        open.connect(new InetSocketAddress("localhost",6666));

        FileChannel channel = new FileInputStream("111.eml").getChannel();

        long time = System.nanoTime();

        channel.transferTo(0,channel.size(),open);

         long time1 = System.nanoTime()-time;

        System.out.println("耗时："+time1);
    }
}
