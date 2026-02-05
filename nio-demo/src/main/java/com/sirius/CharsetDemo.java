package com.sirius;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;

/**
 * Charset（字符集）编解码演示类
 *
 * <p>Charset用于字符和字节之间的转换。在NIO中，数据在Channel中是以字节形式传输的，
 * 因此需要进行编码（字符->字节）和解码（字节->字符）操作。</p>
 *
 * <h3>核心概念：</h3>
 * <ul>
 *   <li><b>Charset</b>：字符集，定义字符和字节之间的映射关系</li>
 *   <li><b>CharsetEncoder</b>：编码器，将CharBuffer转换为ByteBuffer</li>
 *   <li><b>CharsetDecoder</b>：解码器，将ByteBuffer转换为CharBuffer</li>
 * </ul>
 *
 * <h3>常用字符集：</h3>
 * <ul>
 *   <li><b>UTF-8</b>：变长编码，1-4字节，互联网标准</li>
 *   <li><b>UTF-16</b>：定长2或4字节，Java内部使用</li>
 *   <li><b>GBK/GB2312</b>：中文编码，兼容ASCII</li>
 *   <li><b>ISO-8859-1</b>：单字节编码，西欧语言</li>
 * </ul>
 *
 * <h3>编解码流程：</h3>
 * <pre>
 * 字符串 -> CharBuffer -> [编码] -> ByteBuffer -> [解码] -> CharBuffer -> 字符串
 * </pre>
 *
 * @see java.nio.charset.Charset
 * @see java.nio.charset.CharsetEncoder
 * @see java.nio.charset.CharsetDecoder
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CharsetDemo {

    /**
     * 字符编解码演示
     *
     * <p>演示使用UTF-8字符集进行编码和解码操作。</p>
     *
     * @throws CharacterCodingException 如果编解码过程中发生错误
     */
    @Test
    public void demo() throws CharacterCodingException {
        // 获取UTF-8字符集
        final Charset charset = StandardCharsets.UTF_8;

        // 获取编码器：用于将字符转换为字节
        CharsetEncoder charsetEncoder = charset.newEncoder();

        // 获取解码器：用于将字节转换为字符
        CharsetDecoder charsetDecoder = charset.newDecoder();

        // ========== 编码过程 ==========
        // 创建字符缓冲区并写入数据
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("你好，世界❗");
        charBuffer.flip();  // 切换到读模式

        // 编码：CharBuffer -> ByteBuffer
        // UTF-8中，中文通常占3字节，emoji占4字节
        ByteBuffer encode = charsetEncoder.encode(charBuffer);

        // ========== 解码过程 ==========
        // 解码：ByteBuffer -> CharBuffer
        // 注意：编解码必须使用相同的字符集，否则会出现乱码
        CharBuffer decode = charsetDecoder.decode(encode);
        System.out.println(decode.toString());  // 输出: 你好，世界❗
    }

}
