package com.netty.demo12.server;

/**
 * @author Administrator
 * @class ServerBootStap
 * @date 2021/3/1:20:08
 * @decs TODO
 */
public class ServerBootStrap {

    public static void main(String[] args) {
        NettyServer.startServer("localhost",9000);
    }
}
