package com.outstudio.weixin.core.config;

import org.springframework.beans.factory.annotation.Value;
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

    private static String fileSavedPath;

    @Value("${fileSavedPath}")
    public void setFileSavePath(String fileSavedPath) {
        WebConfig.fileSavedPath = fileSavedPath;
    }

    public static String  getFilePath() {
        return fileSavedPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 可以直接使用addResourceLocations 指定磁盘绝对路径，同样可以配置多个位置，注意路径写法需要加上file:
        registry.addResourceHandler("/mp3/**").addResourceLocations(getFilePath()+"mp3/");
        registry.addResourceHandler("/mp4/**").addResourceLocations(getFilePath()+"mp4/");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/open/back/login").setViewName("hide/back/login");
        registry.addViewController("/back/index").setViewName("hide/back/index");
        registry.addViewController("/back/import/audio").setViewName("hide/back/importAudio");
        registry.addViewController("/back/import/video").setViewName("hide/back/importVideo");
        registry.addViewController("/back/import/english").setViewName("hide/back/importEnglish");
        registry.addViewController("/back/view/audio").setViewName("hide/back/viewAudio");
        registry.addViewController("/back/view/video").setViewName("hide/back/viewVideo");
        registry.addViewController("/back/view/english").setViewName("hide/back/viewEnglish");

        registry.addViewController("/page/test/audio").setViewName("hide/page/audio");
        registry.addViewController("/page/test/one").setViewName("hide/page/audioOne");
    }

}
