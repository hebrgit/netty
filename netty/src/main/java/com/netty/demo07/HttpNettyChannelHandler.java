package com.netty.demo07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.net.URL;

/**
 * @ClassName HttpNettyChannelHandler
 * @Author hebo
 * @Date 2021/2/10 13:57
 **/
public class HttpNettyChannelHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {

        /**
         * 判断 httpObject 是不是 http请求
         */
            if (httpObject instanceof HttpRequest){

                System.out.println("httpObject类型："+httpObject.getClass());
                System.out.println("客户端地址："+channelHandlerContext.channel().remoteAddress());

                HttpRequest httpRequest = (HttpRequest) httpObject;

                URI uri = new URI(httpRequest.uri());

                String path = uri.getPath();

                if ("/favicon.ico".equals(path)){
                    System.out.println("不做相应");
                    return;
                }

                HttpVersion version = HttpVersion.HTTP_1_1;
                HttpResponseStatus status = HttpResponseStatus.OK;
                ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);

                DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(version, status, content);
                httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
                httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, httpResponse.content().readableBytes());

                channelHandlerContext.writeAndFlush(httpResponse);
            }


    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
