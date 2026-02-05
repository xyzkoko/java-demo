package cn.com.sirius.starter;

import cn.com.sirius.starter.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starter测试项目启动类
 * 演示自定义Spring Boot Starter的使用
 * @author Sirius
 */
@SpringBootApplication
public class App implements ApplicationRunner {

    @Autowired
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        demoService.start();
    }
}
