package com.netty.demo05;

import com.netty.demo03.ServerSocketChannel01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Administrator
 * @class NewIoServer
 * @date 2021/2/6:23:24
 * @decs TODO
 */
public class NewIoServer {


    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.bind(new InetSocketAddress("localhost",6666));

        serverSocketChannel.configureBlocking(false);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer allocate = ByteBuffer.allocate(4096);
            int readCount = 0;
            while ( readCount != -1){
                try {
                    readCount = socketChannel.read(allocate);
                }catch (Exception e){
                    break;
                }
                allocate.rewind();//将position 置位0 mark 置位-1
            }
        }


    }
}
