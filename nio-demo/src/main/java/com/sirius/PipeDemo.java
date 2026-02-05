package com.sirius;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Pipe（管道）演示类
 *
 * <p>Pipe是NIO中用于线程间通信的单向数据管道。
 * 一个Pipe由一个SinkChannel（写入端）和一个SourceChannel（读取端）组成。</p>
 *
 * <h3>Pipe的组成：</h3>
 * <ul>
 *   <li><b>SinkChannel</b>：管道的写入端，数据从这里流入管道</li>
 *   <li><b>SourceChannel</b>：管道的读取端，数据从这里流出管道</li>
 * </ul>
 *
 * <h3>数据流向：</h3>
 * <pre>
 * 线程A -> SinkChannel -> [Pipe内部缓冲区] -> SourceChannel -> 线程B
 * </pre>
 *
 * <h3>典型应用场景：</h3>
 * <ul>
 *   <li>线程间的数据传递</li>
 *   <li>生产者-消费者模式</li>
 *   <li>任务结果的异步通知</li>
 * </ul>
 *
 * <h3>注意事项：</h3>
 * <ul>
 *   <li>Pipe是单向的，只能从Sink写入，从Source读取</li>
 *   <li>通常在不同线程中使用Sink和Source</li>
 *   <li>需要注意关闭通道以释放资源</li>
 * </ul>
 *
 * @see java.nio.channels.Pipe
 * @see java.nio.channels.Pipe.SinkChannel
 * @see java.nio.channels.Pipe.SourceChannel
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PipeDemo {

    /**
     * Pipe基本使用演示
     *
     * <p>演示通过Pipe在同一线程内发送和接收数据。
     * 实际使用中，通常会在不同线程中操作SinkChannel和SourceChannel。</p>
     *
     * @throws IOException 如果IO操作失败
     */
    @Test
    public void demo() throws IOException {
        // 创建管道
        Pipe pipe = Pipe.open();

        // 创建缓冲区用于数据传输
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // ========== 发送数据（写入Sink端） ==========
        // 获取管道的写入端
        Pipe.SinkChannel sink = pipe.sink();
        // 将数据写入缓冲区
        byteBuffer.put("通过单向管道发送数据".getBytes());
        byteBuffer.flip();  // 切换到读模式
        // 将缓冲区数据写入管道
        sink.write(byteBuffer);

        // ========== 接收数据（从Source端读取） ==========
        // 获取管道的读取端
        Pipe.SourceChannel source = pipe.source();
        // 重置缓冲区准备接收数据
        byteBuffer.flip();
        // 从管道读取数据到缓冲区
        int len = source.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(), 0, len));

        // 关闭通道
        sink.close();
        source.close();
    }
}
