package com.netty.demo03;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName ServerSocketChannel01
 * @Author hebo
 * @Date 2021/2/1 15:49
 **/
public class ServerSocketChannel01 {


    public static void main(String[] args) throws Exception {


        //创建 ServerSocketChannel 通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(6666));

        //设置非阻塞
        serverSocketChannel.configureBlocking(false);


        //创建 Selector 选择器
        Selector selector = Selector.open();

        //serverSocketChannel 注册到选择器中
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true){

            int select = selector.select(1000);

            if (select ==0 ){
                System.out.println("服务器等待1秒，无连接。。。。");
                continue;
            }
            //如果大于0 获取相关的 selectionKey集合
            //如果大于0，说明以获取到关注的事件
            //返回关注时间的集合,通过selectionKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                //迭代获取 selectionKey
                SelectionKey key = iterator.next();
                //根据key 对应的通道发生的事件做相应的处理

                //如果是 OP_ACCEPT (链接），说明有客户端链接服务器
                if(key.isAcceptable()){
                    System.out.println("有一个客户端链接成功：");
                    //获取 SocketChannel 
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //将当前的 SocketChannel 注册到 selection中,同时给socketChannel 关联给buffer
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ,byteBuffer);
                }

                if (key.isReadable()){
                    //通过key反向获取 channel
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    ByteBuffer byteBuffer =(ByteBuffer) key.attachment();
                    socketChannel.read(byteBuffer);

                    System.out.println("客户端："+new String(byteBuffer.array()));
                    byteBuffer.clear();
                }
                iterator.remove();
            }
        }
    }
}
