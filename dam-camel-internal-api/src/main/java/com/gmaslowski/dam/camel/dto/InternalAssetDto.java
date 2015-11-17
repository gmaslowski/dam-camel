package com.gmaslowski.dam.camel.dto;

import java.io.Serializable;

public class InternalAssetDto implements Serializable {

    private String id;
    private String ingestContentUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngestContentUrl() {
        return ingestContentUrl;
    }

    public void setIngestContentUrl(String ingestContentUrl) {
        this.ingestContentUrl = ingestContentUrl;
    }

    public static class InternalAssetDtoBuilder {
        private InternalAssetDto internalAssetDto;

        private InternalAssetDtoBuilder() {
            internalAssetDto = new InternalAssetDto();
        }

        public static InternalAssetDtoBuilder internalAssetDto() {
            return new InternalAssetDtoBuilder();
        }

        public InternalAssetDtoBuilder id(String id) {
            internalAssetDto.setId(id);
            return this;
        }

        public InternalAssetDtoBuilder contentUrl(String contentUrl) {
            internalAssetDto.setIngestContentUrl(contentUrl);
            return this;
        }

        public InternalAssetDto build() {
            return internalAssetDto;
        }
    }

}
