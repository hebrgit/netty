package com.netty.demo12.client;

import com.netty.demo12.service.HelloService;

/**
 * @author Administrator
 * @class ClientBootstrap
 * @date 2021/3/1:20:47
 * @decs TODO
 */
public class ClientBootstrap {

    private static String PROTOCOL = "HelloServer#hello";
    public static void main(String[] args) {

        NettyClient nettyClient = new NettyClient();

//        HelloService helloService =(HelloService) nettyClient.getBean(HelloService.class, PROTOCOL);
//
//        String hello = helloService.hello("hello !!!!");
//        System.out.println(hello);
        nettyClient.initNettyClient();
    }
}
