package com.sirius;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * NIO网络通信演示 - 阻塞模式
 *
 * <p>NIO提供了基于Channel的网络通信方式，相比传统IO更加灵活高效。
 * 本示例演示阻塞模式下的TCP通信。</p>
 *
 * <h3>NIO网络编程三大核心组件：</h3>
 * <ol>
 *   <li><b>Channel（通道）</b>：负责连接和数据传输
 *     <ul>
 *       <li>SocketChannel：TCP客户端通道</li>
 *       <li>ServerSocketChannel：TCP服务端通道</li>
 *       <li>DatagramChannel：UDP通道</li>
 *     </ul>
 *   </li>
 *   <li><b>Buffer（缓冲区）</b>：负责数据的存取</li>
 *   <li><b>Selector（选择器）</b>：用于非阻塞模式，监控多个Channel的IO状态</li>
 * </ol>
 *
 * <h3>阻塞 vs 非阻塞：</h3>
 * <ul>
 *   <li><b>阻塞模式</b>：accept()、read()、write()会阻塞直到完成</li>
 *   <li><b>非阻塞模式</b>：配合Selector使用，不会阻塞，适合高并发场景</li>
 * </ul>
 *
 * <h3>本示例通信流程：</h3>
 * <pre>
 * Client                              Server
 *   |                                    |
 *   |-------- 发送文件内容 ------------>|
 *   |                                    |
 *   |<------- 返回"OK"响应 -------------|
 *   |                                    |
 * </pre>
 *
 * @see java.nio.channels.SocketChannel
 * @see java.nio.channels.ServerSocketChannel
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NetNioDemo1 {

    /**
     * 服务端 - 接收客户端数据并返回响应
     *
     * <p>监听8080端口，接收客户端发送的数据，然后返回"OK"响应。</p>
     *
     * @throws IOException 如果IO操作失败
     */
    @Test
    public void server() throws IOException {
        // 创建服务器通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8080));
        // 获取客户端链接的通道
        SocketChannel socketChannel = serverSocketChannel.accept();
        // 读取客户端数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int len;
        while ((len = socketChannel.read(byteBuffer)) > 0 ) {
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), 0, len));
            byteBuffer.clear();
        }
        // 反馈客户端
        byteBuffer.put("OK".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();
        // 告诉客户端发送完毕
        socketChannel.shutdownOutput();
        // 关闭通道
        socketChannel.close();
        serverSocketChannel.close();
    }

    /**
     * 客户端 - 向服务端发送文件并接收响应
     *
     * <p>连接服务端，发送本地文件内容，然后接收服务端的响应。</p>
     *
     * @throws IOException 如果IO操作失败
     */
    @Test
    public void client() throws IOException {
        // 创建客户端通道并连接服务端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        // 获取本地数据
        FileChannel fileChannel = FileChannel.open(Paths.get("C:\\Users\\35800\\Desktop\\Study.txt"), StandardOpenOption.READ);
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
        fileChannel.read(byteBuffer);
        // 把数据写入通道中
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();
        // 告诉服务器发送完毕
        socketChannel.shutdownOutput();
        // 接收服务器反馈
        int len;
        while ((len = socketChannel.read(byteBuffer)) > 0) {
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), 0, len));
            byteBuffer.clear();
        }
        // 关闭通道
        socketChannel.close();
        fileChannel.close();
    }
}
