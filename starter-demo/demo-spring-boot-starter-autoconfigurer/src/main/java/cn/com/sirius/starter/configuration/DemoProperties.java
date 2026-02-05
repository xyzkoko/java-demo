package cn.com.sirius.starter.configuration;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义Starter配置属性类
 * 对应配置前缀：demo
 * @author Sirius
 */
@ConfigurationProperties(prefix = "demo")
public class DemoProperties {

    private String path;

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
