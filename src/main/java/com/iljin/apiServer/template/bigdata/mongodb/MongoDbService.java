package com.iljin.apiServer.template.bigdata.mongodb;

import com.iljin.apiServer.template.bigdata.asset.BigdataAssetDto;

import java.util.List;

public interface MongoDbService {

	public List<DocumentDto> getMongoDbCollections();

	public List<DocumentDto> getMongoDbCollectionInfo(BigdataAssetDto bigdataAssetDto);

	public List<AssetLotNumDto> getColLotDataCnt();
}

