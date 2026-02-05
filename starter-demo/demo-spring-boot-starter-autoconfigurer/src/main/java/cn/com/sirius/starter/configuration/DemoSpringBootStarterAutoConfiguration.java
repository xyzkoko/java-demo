package cn.com.sirius.starter.configuration;

import cn.com.sirius.starter.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义Starter自动配置类
 * 自动注册DemoService Bean
 * @author Sirius
 */
@Configuration
@EnableConfigurationProperties(DemoProperties.class)
public class DemoSpringBootStarterAutoConfiguration {

    @Autowired
    private DemoProperties demoProperties;

    @Bean
    public DemoService demoService() {
        return new DemoService(demoProperties);
    }
}
