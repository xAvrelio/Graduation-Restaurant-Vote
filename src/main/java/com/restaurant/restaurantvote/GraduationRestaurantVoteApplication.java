package com.restaurant.restaurantvote;

import com.restaurant.restaurantvote.web.converter.DateTimeFormatters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GraduationRestaurantVoteApplication {

    public static void main(String[] args) {
          SpringApplication.run(GraduationRestaurantVoteApplication.class, args);
    }

    @Configuration
    static class MyConfig implements WebMvcConfigurer {

        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addFormatter(new DateTimeFormatters.LocalDateFormatter());
        }
    }


}
