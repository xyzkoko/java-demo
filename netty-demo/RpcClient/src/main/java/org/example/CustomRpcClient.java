package org.example;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty RPC客户端
 * 基于NIO实现的异步非阻塞客户端
 * @author Sirius
 */
public class CustomRpcClient {

    public void start() throws InterruptedException {
        final NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    // Nagle算法关闭
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new CustomChannelInitializer());
            final ChannelFuture future = bootstrap.connect("127.0.0.1", 8888).sync();
            System.out.println("RpcClient启动成功");
            future.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }


    }
}
