package com.gmaslowski.dam.camel.route.asset.ingest;

import com.gmaslowski.dam.camel.domain.asset.mapper.AssetDtoToAssetMapper;
import com.gmaslowski.dam.camel.domain.asset.mapper.AssetToAssetDtoMapper;
import com.gmaslowski.dam.camel.domain.asset.mapper.AssetToInternalAssetDtoMapper;

public class AssetIngestTransformations {

    public static AssetToInternalAssetDtoMapper AssetToInternalAssetDto = new AssetToInternalAssetDtoMapper();
    public static AssetToAssetDtoMapper AssetToAssetDto = new AssetToAssetDtoMapper();
    public static AssetDtoToAssetMapper AssetDtoToAsset = new AssetDtoToAssetMapper();

}
