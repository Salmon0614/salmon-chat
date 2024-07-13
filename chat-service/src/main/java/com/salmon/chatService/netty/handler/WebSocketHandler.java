package com.salmon.chatService.netty.handler;

import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.netty.ChannelContextUtils;
import com.salmon.chatService.netty.NettyService;
import com.salmon.chatService.service.UserService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * websocket处理器
 *
 * @author Salmon
 * @since 2024-07-11
 */
// 用于标记一个 ChannelHandler 可以在多个 ChannelPipeline 中共享。
// 这个注解表明该处理器是线程安全的，因此可以在多个通道（Channel）中重复使用，而不会出现线程安全问题
@ChannelHandler.Sharable
@Slf4j
@Component
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Resource
    private UserService userService;
    @Resource
    private ChannelContextUtils channelContextUtils;
    @Resource
    private NettyService nettyService;

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
        channelContextUtils.removeContext(ctx.channel());
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
        // 在这里处理收到的消息，例如广播给其他连接的客户端
        Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        String account = attribute.get();
        log.debug("收到消息user-{}的消息{}", account, textWebSocketFrame.text());
        nettyService.saveUserHeartBeat(account);
    }

    /**
     * 用户事件触发时调用，例如心跳检测
     *
     * @param ctx 用于处理 I/O 操作和事件的上下文对象
     * @param evt 触发的事件对象，可以是任何类型的事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 如果是握手完成
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String uri = complete.requestUri();
            log.debug("uri: {}", uri);
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
            Map<String, List<String>> parameters = queryStringDecoder.parameters();
            List<String> tokenList = parameters.get("token");
            // 没有token，或者token不合法，拒绝连接
            if (CollectionUtils.isEmpty(tokenList)) {
                ctx.channel().close();
                return;
            }
            String token = tokenList.get(0);
            // 验证 token 的逻辑
            TokenUserVo userToken = userService.getUserToken(token);
            if (!Objects.nonNull(userToken)) {
                ctx.channel().close();
                return;
            }
            log.info("token: {}", token);
            channelContextUtils.addContext(userToken.getAccount(), ctx.channel());
        }
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
