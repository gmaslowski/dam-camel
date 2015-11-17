package com.gmaslowski.dam.camel.tw.route;

import com.gmaslowski.dam.camel.job.ContentTransferJob;
import org.apache.camel.CamelContext;
import org.apache.camel.Converter;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.TypeConverters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Component
public class ContentTransferJobToInputStreamTypeConverter implements TypeConverters {

    private static final Logger log = LoggerFactory.getLogger(ContentTransferJobToInputStreamTypeConverter.class);

    @Autowired
    public ContentTransferJobToInputStreamTypeConverter(CamelContext camelContext) {
        camelContext.getTypeConverterRegistry().addTypeConverters(this);
    }

    @Converter
    public InputStream convertTo(ContentTransferJob ctj) {
        try {
            return new URL(ctj.getAssetDto().getIngestContentUrl()).openStream();
        } catch (IOException ioe) {
            throw new RuntimeCamelException(ioe);
        }
    }
}