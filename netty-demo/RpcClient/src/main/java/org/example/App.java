package org.example;

/**
 * RPC客户端启动类
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        final CustomRpcClient customRpcClient = new CustomRpcClient();
        customRpcClient.start();
    }
}
