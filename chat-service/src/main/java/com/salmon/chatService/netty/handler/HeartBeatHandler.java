package com.salmon.chatService.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳处理器
 *
 * @author Salmon
 * @since 2024-07-11
 */
@Slf4j
public class HeartBeatHandler extends ChannelDuplexHandler {

    /**
     * 心跳处理
     * （处理用户自定义的事件
     * 这些事件可以是通过 IdleStateHandler 触发的空闲事件或其他类型的事件）
     *
     * @param ctx 用于处理 I/O 操作和事件的上下文对象，它提供了许多操作，如写入、刷新、关闭连接，以及访问管道（pipeline）和通道（channel）的各种信息和状态
     * @param evt 触发的事件对象，可以是任何类型的事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {
            if (event.state() == IdleState.READER_IDLE) {
                Channel channel = ctx.channel();
                Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
                String account = attribute.get();
                log.debug("用户{}没有发送心跳，断开连接", account);
                ctx.close();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush("heart");
            }
            //                case WRITER_IDLE:
//                    log.debug("写超时");
//                    // 处理写超时，例如发送心跳包
//                    ctx.writeAndFlush("ping");
//                    break;
//                case ALL_IDLE:
//                    log.debug("读写超时");
//                    // 处理读写超时
//                    ctx.close();
//                    break;
//                default:
//                    break;
        }

    }
}
