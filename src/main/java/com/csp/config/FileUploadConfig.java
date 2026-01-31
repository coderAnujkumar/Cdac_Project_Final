package com.csp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class FileUploadConfig implements WebMvcConfigurer { //customize Spring MVC behavior

    @Override  //we override this method from WebMvcConfigurer interface
    public void addResourceHandlers(ResourceHandlerRegistry registry) {  //This method is used to map a URL path to a physical folder

        // Maps URL path → physical folder
        registry.addResourceHandler("/documents/**")
                .addResourceLocations("file:uploads/documents/");
    }
}
