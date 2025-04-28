package com.medipath.labcare.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.medipath.labcare.interceptor.LoginInterceptor;



@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // only serve your uploads
       
        registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:" + uploadDir + "/");
        // (– you can still map /css/**, /js/**, etc. to classpath:/static/ if needed –)
    }

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        InternalResourceViewResolver r = new InternalResourceViewResolver();
        r.setPrefix("/WEB-INF/views/");
        r.setSuffix(".jsp");
        return r;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**") // Intercept all URLs
                .excludePathPatterns("/login", "/register", "/processLogin", "/css/**", "/js/**", "/images/**"); // Allow some paths
    }
}



