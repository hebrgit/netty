package com.netty.demo01;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName BioServer
 * @Author hebo
 * @Date 2021/1/25 9:53
 **/
public class BioServer {

    public static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
    public static void main(String[] args) throws IOException {


        final LinkedBlockingQueue queue = new LinkedBlockingQueue<>(500);
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(PROCESSORS*2,PROCESSORS*4,60L, TimeUnit.SECONDS,queue);
        final ServerSocket serverSocket = new ServerSocket(80);
        System.out.println("服务启动成功。。。。");

        while (true){
            System.out.println("连接到一个客户端。。。。");
            final Socket accept = serverSocket.accept();
            threadPoolExecutor.execute(new Runnable() {

                InputStream inputStream = null;
                @Override
                public void run() {
                    try {
                        inputStream = accept.getInputStream();
                        byte [] bytes = new byte[1024];
                        int read = inputStream.read(bytes);
                       if (read != -1){
                           System.out.println("输出："+new String(bytes,0,read));
                       }
                    } catch (IOException e) {
                            e.printStackTrace();
                    }finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }
    }

}
