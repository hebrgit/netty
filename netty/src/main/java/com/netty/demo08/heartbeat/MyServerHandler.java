package com.netty.demo08.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @ClassName MyServerHandler
 * @Author hebo
 * @Date 2021/2/24 14:14
 **/
public class MyServerHandler  extends SimpleChannelInboundHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent){

            IdleStateEvent event = (IdleStateEvent)evt;
            String eventType = null;

            switch (event.state()){
                case READER_IDLE:
                    eventType = "读空闲";

                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";

                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";

                    break;
                default:
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"---超时时间---"+eventType);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }
}
