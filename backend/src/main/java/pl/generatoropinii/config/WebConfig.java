package pl.generatoropinii.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS is only needed during development, when `npm run dev` serves the
 * frontend on port 5173 while the backend runs separately on 8080.
 * In the final package (.jar) the frontend is served by the same Spring Boot
 * instance, so CORS is no longer needed - this class can then be removed.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
