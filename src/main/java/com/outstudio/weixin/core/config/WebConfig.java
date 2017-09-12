package com.outstudio.weixin.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by 96428 on 2017/9/12.
 * This in weixin-edu, com.outstudio.weixin.core.config
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将访问/static/** 的路由映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/open/back/login").setViewName("hide/back/login");
        registry.addViewController("/back/index").setViewName("hide/back/index");
        registry.addViewController("/open/back/error").setViewName("hide/back/index");
    }
}
