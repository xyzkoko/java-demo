package com.sirius;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * NIO（New I/O）演示项目启动类
 *
 * <p>本项目演示Java NIO的核心组件和使用方法。NIO是JDK1.4引入的新IO API，
 * 提供了面向缓冲区、基于通道的IO操作方式。</p>
 *
 * <h3>NIO核心组件：</h3>
 * <ul>
 *   <li><b>Buffer</b>：缓冲区，用于数据存取（{@link BufferDemo}）</li>
 *   <li><b>Channel</b>：通道，用于数据传输（{@link ChannelDemo}）</li>
 *   <li><b>Selector</b>：选择器，用于非阻塞IO（{@link NetNioDemo2}）</li>
 *   <li><b>Charset</b>：字符集，用于编解码（{@link CharsetDemo}）</li>
 *   <li><b>Pipe</b>：管道，用于线程间通信（{@link PipeDemo}）</li>
 * </ul>
 *
 * <h3>NIO vs IO：</h3>
 * <table border="1">
 *   <tr><th>特性</th><th>IO</th><th>NIO</th></tr>
 *   <tr><td>数据处理</td><td>面向流（Stream）</td><td>面向缓冲区（Buffer）</td></tr>
 *   <tr><td>阻塞性</td><td>阻塞IO</td><td>支持非阻塞IO</td></tr>
 *   <tr><td>方向性</td><td>单向</td><td>双向（Channel）</td></tr>
 *   <tr><td>多路复用</td><td>不支持</td><td>Selector支持</td></tr>
 * </table>
 *
 * @see BufferDemo 缓冲区演示
 * @see ChannelDemo 通道演示
 * @see NetNioDemo1 阻塞网络IO
 * @see NetNioDemo2 非阻塞网络IO
 */
@SpringBootApplication
public class NioDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NioDemoApplication.class, args);
    }

}
