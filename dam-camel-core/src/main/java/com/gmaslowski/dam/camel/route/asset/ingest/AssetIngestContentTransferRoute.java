package com.gmaslowski.dam.camel.route.asset.ingest;

import com.gmaslowski.dam.camel.dto.InternalAssetDto;
import com.gmaslowski.dam.camel.job.ContentTransferJob;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import static com.gmaslowski.dam.camel.job.JobQueues.TransferContentQueue;
import static com.gmaslowski.dam.camel.route.asset.ingest.AssetIngestPredicates.AssetIngestedWithContent;
import static com.gmaslowski.dam.camel.route.asset.ingest.AssetIngestTransformations.AssetToInternalAssetDto;
import static org.apache.camel.ExchangePattern.InOnly;

@Component
public class AssetIngestContentTransferRoute extends SpringRouteBuilder {

    public static final String directTransferContent = "direct:transferContent";

    @Override
    public void configure() throws Exception {
        from(directTransferContent)
                .routeId("TransferAssetContent")
                .choice()
                .when(AssetIngestedWithContent)
                    .bean(AssetToInternalAssetDto, "map")
                    .process(exchange -> {
                        InternalAssetDto internalAssetDto = exchange.getIn().getBody(InternalAssetDto.class);
                        exchange.getIn().setBody(new ContentTransferJob(internalAssetDto));
                    })
                    .to(InOnly, "activemq:queue:" + TransferContentQueue + "?jmsMessageType=Object")
                .end();
    }
}
