package com.example.closeup.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/"); //.setCachePeriod(60*60*24*365);
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/"); //.setCachePeriod(60*60*24*365);
        registry.addResourceHandler("/imgs/**").addResourceLocations("classpath:/static/imgs/"); //.setCachePeriod(60*60*24*365);

        // http://localhost:8080/imageboard/[이미지경로] 입력하면 이미지가 나옴. 이게 없으면 이미지 접근 불가능
//        registry.addResourceHandler("/shopping/**").addResourceLocations("file:/shopping/");//.setCachePeriod(60*60*24*365);
        // http://localhost:8080/uploads/[이미지경로] 입력하면 이미지가 나옴.
        // 환경에 따라 다른 경로 설정
    }
}