package com.ridoh.Order_Management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to handle Cross-Origin Resource Sharing (CORS) settings.
 * <p>
 * This class defines a {@link WebMvcConfigurer} bean that allows requests from any origin and
 * permits the HTTP methods GET, POST, PUT, and DELETE.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     @Bean
 *     public WebMvcConfigurer webMvcConfigurer() {
 *         return new WebMvcConfigurer() {
 *             @Override
 *             public void addCorsMappings(CorsRegistry registry) {
 *                 registry.addMapping("/**")
 *                         .allowedMethods("GET", "POST", "PUT", "DELETE")
 *                         .allowedOrigins("*");
 *             }
 *         };
 *     }
 * </pre>
 *
 * @author Ridoh
 * @see WebMvcConfigurer
 */
@Configuration
public class CorsConfig {

    /**
     * Configures CORS settings to allow cross-origin requests.
     *
     * @return a {@link WebMvcConfigurer} instance with CORS configurations
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Adds CORS mappings to allow requests from any origin and permits specific HTTP methods.
             *
             * @param registry the {@link CorsRegistry} to configure CORS settings
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*");
            }
        };
    }
}
