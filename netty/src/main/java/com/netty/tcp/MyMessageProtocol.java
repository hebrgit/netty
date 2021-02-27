package com.netty.tcp;

/**
 * @author Administrator
 * @class MyMessageProtocol
 * @date 2021/2/27:21:15
 * @decs 数据协议
 */
public class MyMessageProtocol {

    private int count;
    private byte[] context;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public byte[] getContext() {
        return context;
    }

    public void setContext(byte[] context) {
        this.context = context;
    }
}
