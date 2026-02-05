package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty RPC服务端
 * 基于NIO实现的异步非阻塞服务器，采用主从Reactor模式
 * @author Sirius
 */
public class CustomRpcServer {

    public void start() throws InterruptedException {
        final NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        final NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            final ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    // 指定当服务端的请求处理线程全部用完时,临时存放已经完成了三次握手的请求队列的最大长度
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 指定是否启用心跳机制来维护长连接的不被清楚
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new CustomChannelInitializer());
            final ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("RpcServer启动成功");
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
