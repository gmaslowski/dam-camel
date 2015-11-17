package com.gmaslowski.dam.camel.route.asset.ingest;

import com.gmaslowski.dam.camel.domain.asset.Asset;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

public class AssetIngestPredicates {

    public static final Predicate AssetIngestedWithContent = new Predicate() {
        @Override
        public boolean matches(Exchange exchange) {
            return exchange.getIn().getBody(Asset.class).ingestedWithContent();
        }
    };
}
