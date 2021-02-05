package com.netty.demo02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FileChannel01
 * @Author hebo
 * @Date 2021/1/27 9:43
 **/
public class FileChannel01 {

    public static void main(String[] args) throws Exception {
//    fileOutChannel();
//    fileInChannel();


    }

    private static void fileInChannel() throws Exception {

        //创建文件输入流
        File file = new File("d:\\file.txt");
       FileInputStream fileInputStream = new FileInputStream(file);
       //获取文件通道
        FileChannel channel = fileInputStream.getChannel();
        //创建buffer
        ByteBuffer allocate = ByteBuffer.allocate((int) file.length());
        //将通道数据读取道缓存区
        channel.read(allocate);
        System.out.println(new String(allocate.array()));

    }


    public static void fileOutChannel() throws Exception {
        final String str  = "11111";

        //创建输出流
        FileOutputStream outputStream = new FileOutputStream("d:\\file.txt");
        //通过输出流获取文件通道 FileChannel
        FileChannel channel = outputStream.getChannel();

        //创建ByteBuffer
        ByteBuffer allocate = ByteBuffer.allocate(1000);
        //添加数据
        allocate.put(str.getBytes());
        //反转
        allocate.flip();
        channel.write(allocate);
        outputStream.close();
    }
}
