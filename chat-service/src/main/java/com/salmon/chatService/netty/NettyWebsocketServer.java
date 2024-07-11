package com.salmon.chatService.netty;

import com.salmon.chatService.netty.handler.HeartBeatHandler;
import com.salmon.chatService.netty.handler.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Netty 服务
 *
 * @author Salmon
 * @since 2024-07-11
 */
@Component
@Slf4j
public class NettyWebsocketServer {

    @Value("${ws.port}")
    private Integer wsPort;
    @Resource
    private WebSocketHandler webSocketHandler;

    // 创建BossGroup和WorkerGroup
    // 说明
    // 1. 创建两个线程组，bossGroup和workerGroup
    // 2. bossGroup只是处理连接请求，真正的和客户端业务处理，会交给workerGroup完成
    // 3. 两个都是无限循环
    // 4. bossGroup和workerGroup含有的子线程(NioEventLoop)的个数 默认实际CPU核数 * 2
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private static final EventLoopGroup workGroup = new NioEventLoopGroup(); // 默认 CPU核数 * 2

    @PostConstruct
    public void startServer() {
        new Thread(() -> {
            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(bossGroup, workGroup)
                        .channel(NioServerSocketChannel.class) // 异步IO
                        .handler(new LoggingHandler(LogLevel.DEBUG))
                        .childHandler(new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                ChannelPipeline pipeline = nioSocketChannel.pipeline();
                                // 设置处理器
                                // 1. 对http协议的支持，使用http的编码器、解码器
                                pipeline.addLast(new HttpServerCodec());
                                // 2. 聚合解码 httpRequest/httpContent/lastHttpContent 到 fullHttpRequest
                                // 保证接收的http请求的完整性
                                pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                                // 3. 心跳检测
                                // IdleStateHandler 处理空闲连接的处理器
                                // readerIdleTime 读超时，当连接在指定时间内没有读操作时，会触发 IdleStateEvent.READER_IDLE 事件
                                // writerIdleTime 写超时，连接在指定时间内没有写操作时，会触发 IdleStateEvent.WRITER_IDLE 事件
                                // allIdleTime, 所有类型超时时间，即当连接在指定时间内没有读或写操作时，会触发 IdleStateEvent.ALL_IDLE 事件
                                pipeline.addLast(new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS));
                                pipeline.addLast(new HeartBeatHandler());
                                // 4. 将Http协议升级为ws协议，对websocket支持
                                pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, 64 * 1024, true, true, 10000L));
                                pipeline.addLast(webSocketHandler);
                            }
                        });
                ChannelFuture channelFuture = serverBootstrap.bind(wsPort).sync();
                log.info("netty服务启动成功，端口：{}", wsPort);
                channelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                log.error("启动netty失败", e);
            } finally {
                log.info("netty线程组关闭");
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        }).start();
    }

}
