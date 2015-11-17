package com.gmaslowski.dam.camel.domain.asset.mapper;

import com.gmaslowski.dam.camel.domain.asset.Asset;
import com.gmaslowski.dam.camel.domain.mapper.Mapper;
import com.gmaslowski.dam.camel.dto.AssetDto;

public class AssetToAssetDtoMapper extends Mapper<Asset, AssetDto> {
    @Override
    public AssetDto map(Asset asset) {
        AssetDto assetDto = new AssetDto();

        assetDto.setId(asset.getId());
        assetDto.setRevision(asset.getRevision());
        assetDto.setName(asset.getName());
        assetDto.setContentUrl(asset.getIngestContentUrl());

        return assetDto;
    }
}
