package com.tienda.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                                // Dominios permitidos
                                .allowedOrigins(
                                                "http://localhost:3000", // Desarrollo local
                                                "https://tutienda-front.com")
                                // Métodos HTTP permitidos
                                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                                // Headers permitidos
                                .allowedHeaders(
                                                "Authorization",
                                                "Content-Type",
                                                "Accept",
                                                "X-Requested-With")
                                // Headers visibles al frontend
                                .exposedHeaders("Authorization")
                                // Permitir tokens
                                .allowCredentials(true)
                                // Cache CORS
                                .maxAge(1800);
        }
}