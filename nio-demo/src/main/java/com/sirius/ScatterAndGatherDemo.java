package com.sirius;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Scatter/Gather（分散/聚集）演示类
 *
 * <p>Scatter/Gather是NIO中用于高效处理多个缓冲区的技术。
 * 它允许在一次IO操作中读写多个Buffer。</p>
 *
 * <h3>核心概念：</h3>
 * <ul>
 *   <li><b>Scatter Read（分散读取）</b>：将Channel中的数据依次读入多个Buffer，
 *       一个Buffer填满后自动填充下一个</li>
 *   <li><b>Gather Write（聚集写入）</b>：将多个Buffer中的数据依次写入Channel，
 *       一个Buffer写完后自动写下一个</li>
 * </ul>
 *
 * <h3>典型应用场景：</h3>
 * <ul>
 *   <li>消息头和消息体分离：用不同Buffer存储头和体</li>
 *   <li>协议解析：将固定长度的各字段分别存入不同Buffer</li>
 *   <li>数据分片：将大数据分散到多个Buffer中处理</li>
 * </ul>
 *
 * <h3>优势：</h3>
 * <ul>
 *   <li>减少数据复制：直接将数据读入目标Buffer</li>
 *   <li>简化编程模型：无需手动管理多次读写</li>
 *   <li>提高性能：某些系统可以利用DMA进行优化</li>
 * </ul>
 *
 * @see java.nio.channels.ScatteringByteChannel
 * @see java.nio.channels.GatheringByteChannel
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScatterAndGatherDemo {

    /**
     * 分散读取和聚集写入演示
     *
     * <p>将文件内容分散读入3个100字节的Buffer，然后聚集写入另一个文件。</p>
     *
     * @throws IOException 如果IO操作失败
     */
    @Test
    public void demo() throws IOException {
        RandomAccessFile randomAccessFile1 = new RandomAccessFile("C:\\Users\\35800\\Desktop\\Study.txt", "rw");
        FileChannel channel1 = randomAccessFile1.getChannel();
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer3 = ByteBuffer.allocate(100);

        // 分散读取
        ByteBuffer[] byteBuffers = {byteBuffer1, byteBuffer2, byteBuffer3};
        channel1.read(byteBuffers);

        for (ByteBuffer byteBuffer : byteBuffers) {
            byteBuffer.flip();
            String s = new String(byteBuffer.array(), 0, byteBuffer.limit());
            System.out.println(s);
        }

        // 聚集写入
        RandomAccessFile randomAccessFile2 = new RandomAccessFile("C:\\Users\\35800\\Desktop\\Study2.txt", "rw");
        FileChannel channel2 = randomAccessFile2.getChannel();

        channel2.write(byteBuffers);


    }

}
