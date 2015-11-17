package com.gmaslowski.dam.camel.route.asset.ingest;

import com.gmaslowski.dam.camel.domain.asset.Asset;
import com.gmaslowski.dam.camel.dto.AssetDto;
import com.gmaslowski.dam.camel.infrastructure.couchdb.CouchDbConfiguration;
import com.gmaslowski.dam.camel.infrastructure.jetty.JettyConfiguration;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.gmaslowski.dam.camel.route.asset.ingest.AssetIngestContentTransferRoute.DIRECT_TRANSFER_CONTENT;
import static com.gmaslowski.dam.camel.route.asset.ingest.AssetIngestProcessors.RemoveIdAndRevFromHeaders;
import static com.gmaslowski.dam.camel.route.asset.ingest.AssetIngestProcessors.SetIdAndRevisionInAsset;
import static com.gmaslowski.dam.camel.route.asset.ingest.AssetIngestTransformations.AssetDtoToAsset;
import static com.gmaslowski.dam.camel.route.asset.ingest.AssetIngestTransformations.AssetToAssetDto;
import static org.apache.camel.model.dataformat.JsonLibrary.Gson;

@Component
public class AssetHttpIngestRoute extends SpringRouteBuilder {

    private static final Logger log = LoggerFactory.getLogger(AssetHttpIngestRoute.class);

    private final JettyConfiguration jettyConfiguration;
    private final CouchDbConfiguration couchDbConfiguration;

    @Autowired
    public AssetHttpIngestRoute(JettyConfiguration jettyConfiguration, CouchDbConfiguration couchDbConfiguration) {
        this.jettyConfiguration = jettyConfiguration;
        this.couchDbConfiguration = couchDbConfiguration;
    }

    @Override
    public void configure() throws Exception {

        from("jetty:" + jettyConfiguration.jettyAddress() + "/dam/asset?httpMethodRestrict=POST")
                .routeId("AssetHttpIngest")
                .unmarshal().json(Gson, AssetDto.class)
                .bean(AssetDtoToAsset, "map")
                .routeId("AssetCouchdbPersist")
                .marshal().json(Gson)
                .convertBodyTo(String.class)
                .to("couchdb:" + couchDbConfiguration.assetsDatabase())
                .unmarshal().json(Gson, Asset.class)
                .process(SetIdAndRevisionInAsset)
                .process(RemoveIdAndRevFromHeaders)
                .wireTap(DIRECT_TRANSFER_CONTENT)
                .bean(AssetToAssetDto, "map")
                .marshal().json(Gson)
                .convertBodyTo(String.class);
    }
}
