package com.iljin.apiServer.template.bigdata.mongodb;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AssetLotNumDto {
	String assetId;
	String lotNo;
	Integer dataCnt;

	public AssetLotNumDto(String assetId
						, String lotNo
						, Integer dataCnt)
	{
		this.assetId = assetId;
		this.lotNo = lotNo;
		this.dataCnt = dataCnt;
	}

}
