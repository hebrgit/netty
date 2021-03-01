package com.netty.demo12.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

/**
 * @author Administrator
 * @class ClientHandler
 * @date 2021/3/1:20:19
 * @decs TODO
 */
public class ClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String para;
    private String result;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
//        ctx.writeAndFlush("111111");
        System.out.println("ClientHandler channelActive() 执行");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常信息："+cause.getMessage());
        ctx.close();
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext channelHandlerContext, Object s) throws Exception {
        System.out.println("channelRead0() 方法执行");
       result = s.toString();
       channelHandlerContext.writeAndFlush("111111");
        notify();//唤醒等待线程
    }

    //被代理对象调用，发送消息给服务器，->wait ->等待被唤醒 ->返回结果
    @Override
    public synchronized   Object call() throws Exception {
        System.out.println("call() 方法执行");
        context.writeAndFlush(para);
        wait();//等到channelRead 方法获取到结果后，在被
        return result;
    }

    void setPara(String para){
        this.para = para;
        System.out.println("setPara() 方法执行");
    }
}
