package com.gmaslowski.dam.camel.tw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@SpringBootApplication
@Import(ActiveMQConfiguration.class)
public class TransferWorker {

    public static void main(String[] args) {
        SpringApplication.run(TransferWorker.class);
    }
}
