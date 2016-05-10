package com.gmaslowski.dam.camel.route.asset.ingest;

import com.gmaslowski.dam.camel.domain.asset.mapper.AssetDtoToAssetMapper;
import com.gmaslowski.dam.camel.domain.asset.mapper.AssetToAssetDtoMapper;
import com.gmaslowski.dam.camel.domain.asset.mapper.AssetToInternalAssetDtoMapper;

class AssetIngestTransformations {

    static AssetToInternalAssetDtoMapper AssetToInternalAssetDto = new AssetToInternalAssetDtoMapper();
    static AssetToAssetDtoMapper AssetToAssetDto = new AssetToAssetDtoMapper();
    static AssetDtoToAssetMapper AssetDtoToAsset = new AssetDtoToAssetMapper();
}
