package com.gmaslowski.dam.camel.job;

import com.gmaslowski.dam.camel.dto.InternalAssetDto;

import java.io.Serializable;

public class ContentTransferJob implements Serializable {

    private final InternalAssetDto assetDto;

    public ContentTransferJob(InternalAssetDto assetDto) {
        this.assetDto = assetDto;
    }

    public InternalAssetDto getAssetDto() {
        return assetDto;
    }
}
