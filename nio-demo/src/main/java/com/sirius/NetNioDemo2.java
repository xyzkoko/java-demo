package com.sirius;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * NIO网络通信演示 - 非阻塞模式（Selector多路复用）
 *
 * <p>非阻塞NIO是高性能服务器的核心技术，通过Selector实现单线程管理多个连接。
 * 这是Netty、Tomcat NIO等框架的基础。</p>
 *
 * <h3>Selector（选择器）工作原理：</h3>
 * <ol>
 *   <li>将Channel注册到Selector上，并指定关注的事件</li>
 *   <li>调用select()方法阻塞等待事件发生</li>
 *   <li>获取发生事件的SelectionKey集合</li>
 *   <li>遍历处理每个事件</li>
 *   <li>处理完成后移除SelectionKey</li>
 * </ol>
 *
 * <h3>SelectionKey事件类型：</h3>
 * <ul>
 *   <li><b>OP_ACCEPT</b>：接收连接事件（ServerSocketChannel）</li>
 *   <li><b>OP_CONNECT</b>：连接完成事件（SocketChannel）</li>
 *   <li><b>OP_READ</b>：可读事件</li>
 *   <li><b>OP_WRITE</b>：可写事件</li>
 * </ul>
 *
 * <h3>非阻塞模式优势：</h3>
 * <ul>
 *   <li>单线程管理多连接，减少线程开销</li>
 *   <li>避免线程上下文切换</li>
 *   <li>适合高并发、连接数多但活跃度低的场景</li>
 * </ul>
 *
 * <h3>本示例实现：</h3>
 * <p>服务端使用Selector监听连接和读取事件，实现简单的聊天功能。</p>
 *
 * @see java.nio.channels.Selector
 * @see java.nio.channels.SelectionKey
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NetNioDemo2 {

    /**
     * 服务端 - 使用Selector实现非阻塞IO
     *
     * <p>服务端监听连接事件和读取事件，新连接建立时返回"连接成功"，
     * 收到数据时打印到控制台。</p>
     *
     * @throws IOException 如果IO操作失败
     */
    @Test
    public void server() throws IOException {
        // 创建服务器通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 切换非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8080));
        // 获取选择器
        Selector selector = Selector.open();
        // 将通道注册到选择器上(指定监听连接事件)
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 通过轮询的方式查看选择器上是否有可执行的事件
        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            // 使用迭代器循环（为了后面的删除事件）
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 连接事件
                if (selectionKey.isAcceptable()) {
                    System.out.println("处理连接事件");
                    // 获取客户端链接的通道
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 切换非阻塞模式
                    socketChannel.configureBlocking(false);
                    // 将通道注册到选择器上(指定监听读事件)
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    // 反馈客户端
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    byteBuffer.put("连接成功".getBytes());
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                    byteBuffer.clear();
                }
                // 读事件
                if (selectionKey.isReadable()) {
                    System.out.println("处理读事件");
                    // 获取通道
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    // 创建缓冲区
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len;
                    while ((len = socketChannel.read(byteBuffer)) > 0) {
                        System.out.print("收取数据:");
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }
                }
                // 移除事件
                iterator.remove();
            }
        }
        // 关闭通道
        serverSocketChannel.close();
    }

    /**
     * 客户端 - 非阻塞模式发送消息
     *
     * <p>连接服务端，接收欢迎消息，然后从控制台读取输入发送给服务端。</p>
     *
     * @throws IOException 如果IO操作失败
     */
    @Test
    public void client() throws IOException {
        // 创建客户端通道并连接服务端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        // 切换非阻塞模式（客户端也需要设置）
        socketChannel.configureBlocking(false);
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 接收服务器反馈
        int len;
        while ((len = socketChannel.read(byteBuffer)) > 0) {
            System.out.print("收取数据:");
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), 0, len));
            byteBuffer.clear();
        }
        // 控制台输入
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.next();
            byteBuffer.put(str.getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        // 关闭通道
        socketChannel.close();
    }
}
