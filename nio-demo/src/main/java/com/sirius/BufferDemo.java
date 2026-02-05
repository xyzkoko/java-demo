package com.sirius;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Buffer（缓冲区）演示类
 *
 * <p>Buffer是NIO中用于数据存取的容器，是一个特定基本类型元素的线性有限序列。
 * 所有的NIO数据操作都是通过Buffer进行的。</p>
 *
 * <h3>Buffer的四个核心属性：</h3>
 * <table border="1">
 *   <tr><th>属性</th><th>说明</th><th>关系</th></tr>
 *   <tr><td>capacity</td><td>容量，缓冲区最大存储数据的容量，创建后不可改变</td><td>-</td></tr>
 *   <tr><td>limit</td><td>界限，缓冲区中可操作数据的大小（limit后的数据不能读写）</td><td>limit ≤ capacity</td></tr>
 *   <tr><td>position</td><td>位置，当前正在操作的数据的索引</td><td>position ≤ limit</td></tr>
 *   <tr><td>mark</td><td>标记，记录当前position的位置，可通过reset()恢复</td><td>mark ≤ position</td></tr>
 * </table>
 *
 * <h3>Buffer的核心方法：</h3>
 * <ul>
 *   <li><b>put()</b>：写入数据到缓冲区</li>
 *   <li><b>get()</b>：从缓冲区读取数据</li>
 *   <li><b>flip()</b>：切换到读模式（limit=position, position=0）</li>
 *   <li><b>rewind()</b>：重新读取（position=0, limit不变）</li>
 *   <li><b>clear()</b>：清空缓冲区（position=0, limit=capacity，数据未清除）</li>
 *   <li><b>compact()</b>：压缩缓冲区（未读数据移到开头，准备继续写入）</li>
 * </ul>
 *
 * <h3>直接缓冲区 vs 非直接缓冲区：</h3>
 * <ul>
 *   <li><b>非直接缓冲区（allocate）</b>：在JVM堆内存中分配，IO操作需要经过内核缓冲区复制</li>
 *   <li><b>直接缓冲区（allocateDirect）</b>：在物理内存中分配，减少一次复制，性能更好但创建开销大</li>
 * </ul>
 *
 * @see java.nio.Buffer
 * @see java.nio.ByteBuffer
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BufferDemo {

    /**
     * ByteBuffer基本操作演示
     *
     * <p>演示Buffer的读写模式切换和各属性变化过程。</p>
     */
    @Test
    public void byteBufferDemo() {
        // ========== 1. 创建缓冲区 ==========
        // 创建直接缓冲区（在物理内存中分配）
        // 初始状态：capacity=1024, limit=1024, position=0
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        // 也可以创建非直接缓冲区（在JVM堆内存中分配）
        // ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // ========== 2. 写入数据 ==========
        // 写入12个字节的数据
        // 写入后：capacity=1024, limit=1024, position=12
        byteBuffer.put("Hello World!".getBytes());

        // ========== 3. 切换到读模式 ==========
        // flip()：将limit设为当前position，position设为0
        // 切换后：capacity=1024, limit=12, position=0
        byteBuffer.flip();

        // ========== 4. 读取数据 ==========
        // 读取limit个字节的数据
        // 读取后：capacity=1024, limit=12, position=12
        byte[] dst = new byte[byteBuffer.limit()];
        byteBuffer.get(dst);
        System.out.println(byteBuffer.limit());  // 输出: 12
        System.out.println(new String(dst));     // 输出: Hello World!

        // ========== 5. 重新读取 ==========
        // rewind()：将position设为0，limit不变，可重新读取数据
        // 重置后：capacity=1024, limit=12, position=0
        byteBuffer.rewind();

        // ========== 6. 清空缓冲区 ==========
        // clear()：将position设为0，limit设为capacity
        // 注意：数据并未真正清除，只是"遗忘"了
        // 清空后：capacity=1024, limit=1024, position=0
        byteBuffer.clear();
    }


}
