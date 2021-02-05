package com.netty.demo04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @ClassName ClientNio
 * @Author hebo
 * @Date 2021/2/5 12:22
 **/
public class ClientNio {

    private static  final String IP = "127.0.0.1";
    private static final int PORT = 6666;
    private SocketChannel socketChannel;
    private Selector selector;
    private String userName;

    public ClientNio(){
        try {
            this.selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(IP,PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName = socketChannel.getLocalAddress().toString().substring(1);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(String s){
        ByteBuffer wrap = ByteBuffer.wrap(s.getBytes());
        try {
            socketChannel.write(wrap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData(){

        try {
            int count = selector.select(1000);

            if (count>0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isReadable()){
                        SocketChannel channel = (SocketChannel)key.channel();
                        ByteBuffer allocate = ByteBuffer.allocate(1024);
                        channel.read(allocate);
                        System.out.println("客户端-"+userName+"说:"+new String(allocate.array()));
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientNio clientNio = new ClientNio();
        new Thread(() -> {
            while (true){
                clientNio.readData();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            clientNio.sendMessage(s);
        }
    }
}
