package cn.com.wind.configuration;

import cn.com.wind.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 注册自定义拦截器
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }

}
