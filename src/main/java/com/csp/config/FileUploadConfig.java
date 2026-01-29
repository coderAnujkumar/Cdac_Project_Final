package com.csp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class FileUploadConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Maps URL path → physical folder
        registry.addResourceHandler("/documents/**")
                .addResourceLocations("file:uploads/documents/");
    }
}
