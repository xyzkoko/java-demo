package org.example;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * 客户端Channel初始化器
 * 配置编解码器和业务处理器
 * @author Sirius
 */
public class CustomChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 处理器
        final ChannelPipeline pipeline = ch.pipeline();
        // 添加编码器
        pipeline.addLast(new ObjectEncoder());
        // 添加解码器
        pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                // 默认解码的类加载器
                ClassResolvers.cacheDisabled(null)));
        // 添加自定义处理器
        pipeline.addLast(new CustomRpcClientHandler());
    }
}
