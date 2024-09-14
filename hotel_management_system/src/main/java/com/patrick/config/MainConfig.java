package com.patrick.config;


import com.patrick.interceptor.AdminInterceptor;
import com.patrick.interceptor.AdminInterceptorPublic;
import com.patrick.interceptor.AdminInterceptorTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MainConfig implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;
    @Autowired
    private AdminInterceptorTwo adminInterceptorTwo;
    @Autowired
    private AdminInterceptorPublic adminInterceptorPublic;
    @Value("${patrick.picdir}")
    private String picdir;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(adminInterceptor)
                .excludePathPatterns("/user/**")
                .addPathPatterns("/admin/**");

        registry.addInterceptor(adminInterceptorTwo)
                .excludePathPatterns("/admin/**")
                .addPathPatterns("/user/**");


        registry.addInterceptor(adminInterceptorPublic)
                .addPathPatterns("/info/**");



    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + picdir);
    }

}
