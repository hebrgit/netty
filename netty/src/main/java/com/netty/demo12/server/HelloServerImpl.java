package com.netty.demo12.server;

import com.netty.demo12.service.HelloService;

/**
 * @author Administrator
 * @class HelloServerImpl
 * @date 2021/3/1:19:56
 * @decs TODO
 */
public class HelloServerImpl implements HelloService {
    @Override
    public String hello(String msg) {

        if (msg == null){
            System.out.println("接收到客户端发送的消息");
            return null;
        }else {

            System.out.println("接收到客户端发送的消息:"+msg);
            return msg;
        }
    }
}
