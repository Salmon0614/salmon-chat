package com.salmon.chatService.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * websocket处理器
 *
 * @author Salmon
 * @since 2024-07-11
 */
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 通道就绪后调用，一般用来做初始化
     *
     * @param ctx 用于处理 I/O 操作和事件的上下文对象
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("有新的连接加入...");
        // 你可以在这里添加初始化代码，例如注册用户，欢迎消息等
    }

    /**
     * 当通道断开连接时调用
     *
     * @param ctx 用于处理 I/O 操作和事件的上下文对象
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("有连接断开...");
        // 你可以在这里添加清理代码，例如注销用户，通知其他用户等
    }

    /**
     * 读操作，当从客户端收到一个文本帧时调用
     *
     * @param ctx                用于处理 I/O 操作和事件的上下文对象
     * @param textWebSocketFrame 表示一个文本帧(frame)
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        Channel channel = ctx.channel();
        log.debug("收到消息：{}", textWebSocketFrame.text());
        // 在这里处理收到的消息，例如广播给其他连接的客户端
    }

    /**
     * 用户事件触发时调用，例如心跳检测
     *
     * @param ctx 用于处理 I/O 操作和事件的上下文对象
     * @param evt 触发的事件对象，可以是任何类型的事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 你可以在这里处理特殊事件，例如IdleStateEvent（心跳检测）
    }

    /**
     * 异常处理
     *
     * @param ctx   用于处理 I/O 操作和事件的上下文对象
     * @param cause 抛出的异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("发生异常", cause);
        ctx.close();
    }
}
