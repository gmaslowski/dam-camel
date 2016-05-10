package com.gmaslowski.dam.camel.tw;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.Component;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@Configuration
public class ActiveMQConfiguration {

    @Value("${activemq.address}")
    private String activeMQAddress;

    @Value("${activemq.port}")
    private String activeMQPort;

    @Value("${activemq.user}")
    private String activeMQUser;

    @Value("${activemq.password}")
    private String activeMQPassword;

    @Bean
    ActiveMQConnectionFactory activeMQConnectionFactory() {
        return new ActiveMQConnectionFactory(activeMQUser, activeMQPassword, "tcp://" + activeMQAddress + ":" + activeMQPort);
    }

    @Bean
    Component activemq(ConnectionFactory activeMQConnectionFactory) {
        return JmsComponent.jmsComponentAutoAcknowledge(activeMQConnectionFactory);
    }

}
