package com.gmaslowski.dam.camel.domain.asset.mapper;

import com.gmaslowski.dam.camel.domain.asset.Asset;
import com.gmaslowski.dam.camel.domain.mapper.Mapper;
import com.gmaslowski.dam.camel.dto.AssetDto;

public class AssetDtoToAssetMapper extends Mapper<AssetDto, Asset> {

    @Override
    public Asset map(AssetDto assetDto) {
        Asset asset = new Asset();

        asset.setId(assetDto.getId());
        asset.setRevision(assetDto.getRevision());
        asset.setName(assetDto.getName());
        asset.setIngestContentUrl(assetDto.getContentUrl());

        return asset;
    }
}
