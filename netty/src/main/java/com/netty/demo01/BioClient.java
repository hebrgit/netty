package com.netty.demo01;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @ClassName BioClient
 * @Author hebo
 * @Date 2021/1/25 10:35
 **/
public class BioClient {

    public static void main(String[] args) throws IOException {

        System.out.println("启动客户端。。。");
        Socket socket = new Socket("localhost",80);
        OutputStream outputStream = socket.getOutputStream();

            try {
                Scanner scanner = new Scanner(System.in);
                while (true){
                    String s = scanner.nextLine();
                    if ("close".equals(s)){
                        break;
                    }
                    byte[] bytes = s.getBytes();
                    outputStream.write(bytes);
                    outputStream.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
            }


    }
}
