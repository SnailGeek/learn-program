package com.lagou.rpc.consumer.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

public class RpcClientHandler extends SimpleChannelInboundHandler<String> implements Callable {
    private ChannelHandlerContext ctx;

    private String requestMsg;

    private String responseMsg;

    /**
     * 通道连接就绪事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    /**
     * 通道读取就绪事件
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected synchronized void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        responseMsg = msg;
        // 唤醒线程
        notify();
    }

    /**
     * 发送消息到服务端
     *
     * @return
     */
    @Override
    public synchronized Object call() throws Exception {
        ctx.writeAndFlush(requestMsg);
        //线程等待
        wait();
        return responseMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }
}
