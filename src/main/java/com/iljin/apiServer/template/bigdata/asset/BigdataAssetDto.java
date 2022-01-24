package com.iljin.apiServer.template.bigdata.asset;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class BigdataAssetDto implements Serializable {
	String siteId;
	String assetId;
	String assetNm;
	Integer assetGroup;
	String upAssetId;
	String assetDesc;
	String assetStatCd;
	String modelConfirmYn;

	String tagId;
	String dataTypeCd;
	String mappingKey;

	String siteNm;
	String tagNm;
	Integer dataCnt;

	/*
	 * 태그현황 조회
	 * */
	public BigdataAssetDto(String siteId
			, String siteNm
			, String assetId
			, String assetNm
			, String tagId
			, String tagNm
			, String dataTypeCd
			, Integer dataCnt
			, String assetDesc
			)
	{
		this.siteId = siteId;
		this.siteNm = siteNm;
		this.assetId = assetId;
		this.assetNm = assetNm;
		this.tagId = tagId;
		this.tagNm = tagNm;
		this.dataTypeCd = dataTypeCd;
		this.dataCnt = dataCnt;
		this.assetDesc = assetDesc;
	}


	String alarmId;
	String alarmDataTime;
	Double alarmVal;
	String alarmLevelCd;
	String alarmDesc;
	String importanceCd;
	String confirmYn;

	/*
	 * 알람현황 조회
	 * */
	public BigdataAssetDto(String siteId
			, String siteNm
			, String assetId
			, String assetNm
			, String tagId
			, String tagNm
			, String alarmId
			, String alarmDataTime
			, Double alarmVal
			, String alarmLevelCd
			, String alarmDesc
			, String importanceCd
			, String confirmYn
	)
	{
		this.siteId = siteId;
		this.siteNm = siteNm;
		this.assetId = assetId;
		this.assetNm = assetNm;
		this.tagId = tagId;
		this.tagNm = tagNm;
		this.alarmId = alarmId;
		this.alarmDataTime = alarmDataTime;
		this.alarmVal = alarmVal;
		this.alarmLevelCd = alarmLevelCd;
		this.alarmDesc = alarmDesc;
		this.importanceCd = importanceCd;
		this.confirmYn = confirmYn;
	}

	Integer level;
	/*
	 * 자산관리 트리뷰 조회
	 * */
	public BigdataAssetDto(String siteId
			, String assetId
			, String assetNm
			, Integer assetGroup
			, String assetDesc
			, String upAssetId
			, String assetStatCd
			, Integer level
	)
	{
		this.siteId = siteId;
		this.assetId = assetId;
		this.assetNm = assetNm;
		this.assetGroup = assetGroup;
		this.assetDesc = assetDesc;
		this.upAssetId = upAssetId;
		this.assetStatCd = assetStatCd;
		this.level = level;
	}


	/*
	 * 자산관리 상세
	 * */
	public BigdataAssetDto(String siteId
			, String assetId
			, String assetNm
			, Integer assetGroup
			, String assetDesc
			, String upAssetId
			, String assetStatCd
	)
	{
		this.siteId = siteId;
		this.assetId = assetId;
		this.assetNm = assetNm;
		this.assetGroup = assetGroup;
		this.assetDesc = assetDesc;
		this.upAssetId = upAssetId;
		this.assetStatCd = assetStatCd;
	}

	String fileName;
	String storedName;
	String downloadUrl;

	/*
	 * 자산관리 상세
	 * */
	public BigdataAssetDto(String siteId
			, String assetId
			, String assetNm
			, Integer assetGroup
			, String assetDesc
			, String upAssetId
			, String assetStatCd
			, String modelConfirmYn
			, String fileName
			, String storedName
			, String downloadUrl
	)
	{
		this.siteId = siteId;
		this.assetId = assetId;
		this.assetNm = assetNm;
		this.assetGroup = assetGroup;
		this.assetDesc = assetDesc;
		this.upAssetId = upAssetId;
		this.assetStatCd = assetStatCd;
		this.modelConfirmYn = modelConfirmYn;
		this.fileName = fileName;
		this.storedName = storedName;
		this.downloadUrl = downloadUrl;
	}

	String alarmLevelNm;

	/*
	 * 알람현황 조회 (모니터링)
	 * */
	public BigdataAssetDto(String siteId
			, String assetId
			, String alarmId
			, String alarmDataTime
			, Double alarmVal
			, String alarmLevelCd
			, String alarmLevelNm
			, String alarmDesc
	)
	{
		this.siteId = siteId;
		this.assetId = assetId;
		this.alarmId = alarmId;
		this.alarmDataTime = alarmDataTime;
		this.alarmVal = alarmVal;
		this.alarmLevelCd = alarmLevelCd;
		this.alarmLevelNm = alarmLevelNm;
		this.alarmDesc = alarmDesc;
	}
}

