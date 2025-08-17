package tour.tourpackages;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/travel-agency/**")
                .addResourceLocations("file:/Users/Rishitha U M/OneDrive/Desktop/tourpackages/tourpackages/travel-agency/");
    }
}
