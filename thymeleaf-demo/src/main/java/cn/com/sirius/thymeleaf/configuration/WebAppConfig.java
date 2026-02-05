package cn.com.sirius.thymeleaf.configuration;

import cn.com.sirius.thymeleaf.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置类
 * 注册拦截器
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 注册自定义拦截器
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }

}
