package cn.com.sirius.demo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * HTTP服务器演示类
 * 使用JDK内置HttpServer创建简单的HTTP服务，监听8081端口
 * 实现ApplicationRunner接口，在Spring Boot启动后自动运行
 */
@Component
public class ServerDemo implements ApplicationRunner {

    /**
     * Spring Boot启动完成后执行
     * 创建HTTP服务器并注册请求处理器
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new DemoHandler());
        server.start();
    }

    /**
     * HTTP请求处理器
     * 处理所有进入的HTTP请求，返回"hello world"响应
     */
    static class DemoHandler implements HttpHandler {

        /**
         * 处理HTTP请求
         * @param httpExchange HTTP交换对象，包含请求和响应信息
         */
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "hello world";
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
