package com.gmaslowski.dam.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@SpringBootApplication
@Import({
        ActiveMQConfiguration.class,
        Routes.class
})
public class Core {

    public static void main(String[] args) {
        SpringApplication.run(Core.class);
    }
}
