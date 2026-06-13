package com.convivir.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Detectamos el sistema operativo para hacer el puente web -> carpeta física
        String contexPath = System.getProperty("os.name").toLowerCase().contains("win")
                ? "file:///C:/convivir_uploads/"
                : "file:/opt/render/project/src/uploads/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(contexPath);
    }
}