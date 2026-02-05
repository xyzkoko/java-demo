package cn.com.sirius;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TCP/IP服务端Spring Boot启动类
 * 演示基于Spring Boot的TCP服务器应用
 */
@SpringBootApplication
public class TcpIpServerSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcpIpServerSpringApplication.class, args);
    }

}
