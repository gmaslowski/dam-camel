package com.gmaslowski.dam.camel.route.asset.ingest;

import com.gmaslowski.dam.camel.domain.asset.Asset;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class AssetIngestProcessors {

    private static final String couchDbId = "CouchDbId";
    private static final String couchDbRev = "CouchDbRev";

    public static Processor SetIdAndRevisionInAsset = new Processor() {

        @Override
        public void process(Exchange exchange) throws Exception {
            String assetId = exchange.getIn().getHeader(couchDbId, String.class);
            String assetRevision = exchange.getIn().getHeader(couchDbRev, String.class);

            Asset asset = exchange.getIn().getBody(Asset.class);
            asset.setId(assetId);
            asset.setRevision(assetRevision);
        }
    };

    public static Processor RemoveIdAndRevFromHeaders = new Processor() {

        @Override
        public void process(Exchange exchange) throws Exception {
            exchange.getIn().removeHeaders(couchDbId);
            exchange.getIn().removeHeaders(couchDbRev);
        }
    };

}
