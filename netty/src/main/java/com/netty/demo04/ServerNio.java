package com.netty.demo04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName ChatRoomNio
 * @Author hebo
 * @Date 2021/2/4 11:43
 **/
public class ServerNio {

    public ServerSocketChannel serverChannel;
    public Selector selector;

    public ServerNio(){
        try {
            serverChannel = ServerSocketChannel.open();
            selector = Selector.open();
            serverChannel.bind(new InetSocketAddress("127.0.0.1",6666));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void clientAcceptHandler(){
        while (true){
            try {
                int count = selector.select(1000);
                if (count == 0){
                    continue;
                }
                if (count>0){
                    //获取事件的 selectionKeys
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    //迭代遍历 selectionKeys
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        //判断如果时 OP_ACCEPT （客户端连接 事件）
                        if (key.isAcceptable()){
                            //获取 SocketChannel 并注册到 选择器中 selector
                            SocketChannel socketChannel = serverChannel.accept();
                            socketChannel.configureBlocking(false);
                            ByteBuffer allocate = ByteBuffer.allocate(1024);
                            socketChannel.register(selector,SelectionKey.OP_READ,allocate);
                            System.out.println("客户端-"+socketChannel.getRemoteAddress()+":已上线");
                        }
                        //判断如果时 OP_READ （读取客户端消息 事件）
                        if (key.isReadable()){
                            readData(key);
                        }
                        iterator.remove();
                    }
                }
            }catch (IOException e){

            }

        }

    }

    public void  readData(SelectionKey key){
        SocketChannel socketChannel = null;
        try {
            socketChannel =(SocketChannel) key.channel();
            ByteBuffer byteBuffer =(ByteBuffer) key.attachment();
            socketChannel.read(byteBuffer);
            String message = new String(byteBuffer.array());
            SocketAddress remoteAddress = socketChannel.getRemoteAddress();
            System.out.println("客户端-"+ remoteAddress +":"+ message);
            sendMessage(selector,message,socketChannel);

        }catch (IOException e){
            try {
                System.out.println("客户端-"+ socketChannel.getRemoteAddress() +":已离线");
                //取消注册
                key.cancel();
                //关闭通道
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }


    public void sendMessage(Selector selector,String message,SocketChannel socketChannel) throws IOException {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()){
            SelectionKey selectionKey = iterator.next();
            Channel channel = selectionKey.channel();
            if (channel instanceof SocketChannel && channel!=socketChannel){
                SocketChannel socketChannel1 = (SocketChannel) channel;
                ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());
                socketChannel1.write(byteBuffer);
            }
        }

    }

    public static void main(String[] args) {
        ServerNio serverNio = new ServerNio();
        serverNio.clientAcceptHandler();
    }
}
