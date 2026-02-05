package com.sirius;

import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Channel（通道）演示类
 *
 * <p>Channel是NIO中用于数据传输的通道，类似于传统IO中的Stream，
 * 但Channel是双向的（可读可写），而Stream是单向的。</p>
 *
 * <h3>Channel的主要实现类：</h3>
 * <ul>
 *   <li><b>FileChannel</b>：用于文件读写，不支持非阻塞模式</li>
 *   <li><b>SocketChannel</b>：用于TCP网络通信（客户端）</li>
 *   <li><b>ServerSocketChannel</b>：用于TCP网络通信（服务端）</li>
 *   <li><b>DatagramChannel</b>：用于UDP网络通信</li>
 * </ul>
 *
 * <h3>获取Channel的方式：</h3>
 * <ul>
 *   <li>通过流对象的getChannel()方法</li>
 *   <li>通过FileChannel.open()静态方法</li>
 *   <li>通过Files工具类的newByteChannel()方法</li>
 * </ul>
 *
 * <h3>数据传输方式对比：</h3>
 * <table border="1">
 *   <tr><th>方式</th><th>说明</th><th>特点</th></tr>
 *   <tr><td>传统IO</td><td>使用InputStream/OutputStream</td><td>简单直接</td></tr>
 *   <tr><td>非直接缓冲区</td><td>allocate() + Channel</td><td>数据经过JVM堆</td></tr>
 *   <tr><td>直接缓冲区</td><td>allocateDirect() + Channel</td><td>零拷贝思想</td></tr>
 *   <tr><td>内存映射</td><td>MappedByteBuffer</td><td>直接操作物理内存</td></tr>
 *   <tr><td>通道传输</td><td>transferTo/transferFrom</td><td>最高效的文件复制</td></tr>
 * </table>
 *
 * @see java.nio.channels.FileChannel
 * @see java.nio.MappedByteBuffer
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChannelDemo {

    /**
     * 方式1：传统IO流读取文件
     *
     * <p>使用FileInputStream读取文件内容，这是最传统的方式。</p>
     */
    @Test
    public void inputStreamDemo() {
        File file = new File("C:\\Users\\35800\\Desktop\\Study.txt");
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[inputStream.available()];
            StringBuffer stringBuffer = new StringBuffer();
            while (inputStream.read(bytes) != -1) {
                stringBuffer.append(new String(bytes));
            }
            System.out.println(stringBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式2：Channel + 非直接缓冲区
     *
     * <p>使用Channel配合在JVM堆中分配的缓冲区读取文件。
     * 数据流向：磁盘 -> 内核缓冲区 -> JVM堆缓冲区 -> 应用程序</p>
     */
    @Test
    public void inputStreamDemo2() {
        File file = new File("C:\\Users\\35800\\Desktop\\Study.txt");
        try (FileInputStream inputStream = new FileInputStream(file);
             // 创建channel通道
             FileChannel channel = inputStream.getChannel()) {
            // 分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            StringBuffer stringBuffer = new StringBuffer();
            while (channel.read(byteBuffer) != -1) {
                // 切换读模式
                byteBuffer.flip();
                stringBuffer.append(StandardCharsets.UTF_8.decode(byteBuffer).toString());
                byteBuffer.clear();
            }
            System.out.println(stringBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式3：Channel + 直接缓冲区
     *
     * <p>使用Channel配合在物理内存中分配的直接缓冲区读取文件。
     * 数据流向：磁盘 -> 物理内存缓冲区 -> 应用程序（减少一次复制）</p>
     */
    @Test
    public void inputStreamDemo3() {
        File file = new File("C:\\Users\\35800\\Desktop\\Study.txt");
        try (FileInputStream inputStream = new FileInputStream(file);
             FileChannel channel = inputStream.getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
            StringBuffer stringBuffer = new StringBuffer();
            while (channel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                stringBuffer.append(StandardCharsets.UTF_8.decode(byteBuffer).toString());
                byteBuffer.clear();
            }
            System.out.println(stringBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式4：Channel直接读写文件复制
     *
     * <p>使用FileChannel.open()打开通道，通过直接缓冲区实现文件复制。
     * 这是NIO推荐的文件操作方式。</p>
     */
    @Test
    public void inputStreamDemo4() throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("C:\\Users\\35800\\Desktop\\Study.txt"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("C:\\Users\\35800\\Desktop\\Study2.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }


    /**
     * 方式5：内存映射文件（MappedByteBuffer）
     *
     * <p>将文件直接映射到内存，对内存的操作直接反映到文件。
     * 适合大文件的随机读写，是最高效的文件操作方式之一。</p>
     *
     * <p>映射模式：</p>
     * <ul>
     *   <li>READ_ONLY：只读映射</li>
     *   <li>READ_WRITE：读写映射</li>
     *   <li>PRIVATE：私有映射（写时复制）</li>
     * </ul>
     */
    @Test
    public void inputStreamDemo5() throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("C:\\Users\\35800\\Desktop\\Study.txt"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("C:\\Users\\35800\\Desktop\\Study2.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);

            MappedByteBuffer inMappedByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMappedByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
            // 直接对缓冲区进行数据读写
            byte[] bytes = new byte[inMappedByteBuffer.limit()];
            inMappedByteBuffer.get(bytes);
            outMappedByteBuffer.put(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    /**
     * 方式6：通道间直接传输（零拷贝）
     *
     * <p>使用transferTo()或transferFrom()在通道之间直接传输数据，
     * 不需要经过用户空间缓冲区，是最高效的文件复制方式。</p>
     *
     * <p>底层利用操作系统的sendfile()系统调用实现零拷贝。</p>
     */
    @Test
    public void inputStreamDemo6() throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("C:\\Users\\35800\\Desktop\\Study.txt"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("C:\\Users\\35800\\Desktop\\Study2.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
            // 通道之间数据传输
            inChannel.transferTo(0, inChannel.size(), outChannel);
            // outChannel.transferFrom(inChannel, 0, inChannel.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }
}
