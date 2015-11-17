package com.gmaslowski.dam.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class Core {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Core.class);

        CamelSpringBootApplicationController applicationController = applicationContext.getBean(CamelSpringBootApplicationController.class);
        applicationController.blockMainThread();
    }

    @Bean
    CamelContext camelContext(ApplicationContext context) {
        return new SpringCamelContext(context);
    }
}
