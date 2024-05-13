package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${com.demo.upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ensuring the path ends with a forward slash
        String uploadDirectory = uploadPath;
        if (!uploadDirectory.endsWith("/")) {
            uploadDirectory += "/";
        }

        // Expose the upload path as a resource path
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadDirectory);
    }
}