package com.gmaslowski.dam.camel.infrastructure.couchdb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CouchDbConfiguration {

    @Value("${couchdb.port}")
    private String port;

    @Value("${couchdb.address}")
    private String address;


    @Value("${couchdb.database}")
    private String assetDb;


    public String assetsDatabase() {
        return "http://" + address + ":" + port + "/" + assetDb;
    }

}
