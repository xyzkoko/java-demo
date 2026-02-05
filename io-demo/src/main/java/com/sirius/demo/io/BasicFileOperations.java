package com.sirius.demo.io;

import java.io.*;

/**
 * Demonstrates basic file operations using standard IO.
 *
 * 中文说明：该类演示使用传统 java.io API 进行文件的写入与读取。
 * - 使用 BufferedWriter/BufferedReader 简化字符流写读操作。
 * - 示例为演示用途，未做复杂的异常恢复或并发控制。
 */
public class BasicFileOperations {

    public static void main(String[] args) {
        String filePath = "example.txt";
        String content = "Hello, this is a basic file operation demo!";

        // Write to a file
        writeFile(filePath, content);

        // Read from a file
        readFile(filePath);
    }

    /**
     * 将字符串内容写入到指定路径的文件（覆盖模式）。
     *
     * @param filePath 文件路径（相对或绝对）
     * @param content  要写入的文本内容
     */
    public static void writeFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("File written successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * 按行读取指定文件并将内容输出到控制台。
     *
     * @param filePath 要读取的文件路径
     */
    public static void readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Reading file: " + filePath);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
