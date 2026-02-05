package org.example;

/**
 * RPC服务端启动类
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        final CustomRpcServer rpcServer = new CustomRpcServer();
        rpcServer.start();
    }

}
