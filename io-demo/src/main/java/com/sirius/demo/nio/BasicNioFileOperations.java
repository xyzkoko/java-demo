package com.sirius.demo.nio;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

/**
 * NIO 基本文件操作示例，演示文件的写入、按行读取和目录列举。
 * 为示例简洁性，代码使用 try-with-resources 管理可能返回的 Stream，避免资源泄露。
 */
public class BasicNioFileOperations {

    /**
     * 程序入口：示例演示顺序为写入 -> 读取 -> 列出当前目录文件。
     */
    public static void main(String[] args) {
        Path filePath = Paths.get("example-nio.txt");
        String content = "Hello, this is a basic NIO file operation demo!";

        // 写入文件（覆盖或创建）
        writeFile(filePath, content);

        // 读取并打印文件内容
        readFile(filePath);

        // 列出当前目录下的文件（仅当前层级）
        listFiles(Paths.get("."));
    }

    /**
     * 使用 NIO 将文本写入到指定文件（覆盖或创建）。
     *
     * @param filePath 要写入的文件路径
     * @param content  文本内容
     */
    public static void writeFile(Path filePath, String content) {
        try {
            Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("File written successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * 按行读取并打印文件内容。
     *
     * @param filePath 要读取的文件路径
     */
    public static void readFile(Path filePath) {
        System.out.println("Reading file: " + filePath);
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * 列出指定目录下的文件（仅当前层级）。
     *
     * @param dirPath 要列出的目录路径
     */
    public static void listFiles(Path dirPath) {
        System.out.println("Listing files in directory: " + dirPath);
        try (Stream<Path> stream = Files.list(dirPath)) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error listing files: " + e.getMessage());
        }
    }
}
