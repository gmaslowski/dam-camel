package com.gmaslowski.dam.camel.domain.asset.mapper;

import com.gmaslowski.dam.camel.domain.asset.Asset;
import com.gmaslowski.dam.camel.domain.mapper.Mapper;
import com.gmaslowski.dam.camel.dto.InternalAssetDto;

import static com.gmaslowski.dam.camel.dto.InternalAssetDto.InternalAssetDtoBuilder.internalAssetDto;

public class AssetToInternalAssetDtoMapper extends Mapper<Asset, InternalAssetDto> {

    @Override
    public InternalAssetDto map(Asset asset) {
        return internalAssetDto()
                .id(asset.getId())
                .contentUrl(asset.getIngestContentUrl())
                .build();
    }
}
