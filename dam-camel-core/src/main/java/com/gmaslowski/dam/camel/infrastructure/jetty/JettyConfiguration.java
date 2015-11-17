package com.gmaslowski.dam.camel.infrastructure.jetty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JettyConfiguration {

    @Value("${server.port}")
    private String port;

    @Value("${server.address}")
    private String address;

    public String jettyAddress() {
        return "http://" + address + ":" + port;
    }
}
