package com.gmaslowski.dam.camel.tw.route;

import com.gmaslowski.dam.camel.job.ContentTransferJob;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import static com.gmaslowski.dam.camel.job.JobQueues.TransferContentQueue;

@Component
public class TransferContentRoute extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:queue:" + TransferContentQueue + "?jmsMessageType=Object")
                .process(exchange -> {
                    ContentTransferJob ctj = exchange.getIn().getBody(ContentTransferJob.class);
                    exchange.getIn().setHeader("CamelFileName", ctj.getAssetDto().getId() + "/original.jpg");
                })
                .to("file:/tmp/dam-camel-storage/?autoCreate=true");
    }
}
