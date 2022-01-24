
package com.iljin.apiServer.template.bigdata.tag;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class BigdataAssetTagDto implements Serializable {

	String siteId;
	String assetId;
	String tagId;
	String tagNm;
	String tagDesc;
	String dataTypeCd;
	Integer cycle;
	String importanceCd;
	String alias;
	Integer inputMinVal;
	Integer inputMaxVal;
	String format;
	Double alarmInfoMinVal;
	Double alarmInfoMaxVal;
	Double alarmWormMinVal;
	Double alarmWormMaxVal;
	Double alarmEmerMinVal;
	Double alarmEmerMaxVal;
	String useYn;
	Integer dataCnt;
	String mappingKey;

	/*
	 * 연결 조회
	 * */
	public BigdataAssetTagDto(String siteId
			, String assetId
			, String tagId
			, String tagNm
			, String tagDesc
			, String dataTypeCd
			, Integer cycle
			, String importanceCd
			, String alias
			, Integer inputMinVal
			, Integer inputMaxVal
			, String format
			, Double alarmInfoMinVal
			, Double alarmInfoMaxVal
			, Double alarmWormMinVal
			, Double alarmWormMaxVal
			, Double alarmEmerMinVal
			, Double alarmEmerMaxVal
			, String useYn
			, Integer dataCnt
			, String mappingKey)
	{
		this.siteId = siteId;
		this.assetId = assetId;
		this.tagId = tagId;
		this.tagNm = tagNm;
		this.tagDesc = tagDesc;
		this.dataTypeCd = dataTypeCd;
		this.cycle = cycle;
		this.importanceCd = importanceCd;
		this.alias = alias;
		this.inputMinVal = inputMinVal;
		this.inputMaxVal = inputMaxVal;
		this.format = format;
		this.alarmInfoMinVal = alarmInfoMinVal;
		this.alarmInfoMaxVal = alarmInfoMaxVal;
		this.alarmWormMinVal = alarmWormMinVal;
		this.alarmWormMaxVal = alarmWormMaxVal;
		this.alarmEmerMinVal = alarmEmerMinVal;
		this.alarmEmerMaxVal = alarmEmerMaxVal;
		this.useYn = useYn;
		this.dataCnt = dataCnt;
		this.mappingKey = mappingKey;
	}

	String assetNm;

	/*
	 * 모델링전 태그 입력값 체크 조회
	 * */
	public BigdataAssetTagDto(String siteId
			, String assetId
			, String tagId
			, String tagNm
			, String tagDesc
			, String dataTypeCd
			, Integer cycle
			, String importanceCd
			, String alias
			, Integer inputMinVal
			, Integer inputMaxVal
			, String format
			, Double alarmInfoMinVal
			, Double alarmInfoMaxVal
			, Double alarmWormMinVal
			, Double alarmWormMaxVal
			, Double alarmEmerMinVal
			, Double alarmEmerMaxVal
			, String useYn
			, Integer dataCnt
			, String mappingKey
			, String assetNm)
	{
		this.siteId = siteId;
		this.assetId = assetId;
		this.tagId = tagId;
		this.tagNm = tagNm;
		this.tagDesc = tagDesc;
		this.dataTypeCd = dataTypeCd;
		this.cycle = cycle;
		this.importanceCd = importanceCd;
		this.alias = alias;
		this.inputMinVal = inputMinVal;
		this.inputMaxVal = inputMaxVal;
		this.format = format;
		this.alarmInfoMinVal = alarmInfoMinVal;
		this.alarmInfoMaxVal = alarmInfoMaxVal;
		this.alarmWormMinVal = alarmWormMinVal;
		this.alarmWormMaxVal = alarmWormMaxVal;
		this.alarmEmerMinVal = alarmEmerMinVal;
		this.alarmEmerMaxVal = alarmEmerMaxVal;
		this.useYn = useYn;
		this.dataCnt = dataCnt;
		this.mappingKey = mappingKey;
		this.assetNm = assetNm;
	}

	String modelConfirmYn;

	/*
	 * 태그 팝업 조회
	 * */
	public BigdataAssetTagDto(String siteId
			, String assetId
			, String tagId
			, String tagNm
			, String tagDesc
			, String dataTypeCd
			, Integer cycle
			, String importanceCd
			, String alias
			, Integer inputMinVal
			, Integer inputMaxVal
			, String format
			, Double alarmInfoMinVal
			, Double alarmInfoMaxVal
			, Double alarmWormMinVal
			, Double alarmWormMaxVal
			, Double alarmEmerMinVal
			, Double alarmEmerMaxVal
			, String useYn
			, Integer dataCnt
			, String mappingKey
			, String assetNm
			, String modelConfirmYn)
	{
		this.siteId = siteId;
		this.assetId = assetId;
		this.tagId = tagId;
		this.tagNm = tagNm;
		this.tagDesc = tagDesc;
		this.dataTypeCd = dataTypeCd;
		this.cycle = cycle;
		this.importanceCd = importanceCd;
		this.alias = alias;
		this.inputMinVal = inputMinVal;
		this.inputMaxVal = inputMaxVal;
		this.format = format;
		this.alarmInfoMinVal = alarmInfoMinVal;
		this.alarmInfoMaxVal = alarmInfoMaxVal;
		this.alarmWormMinVal = alarmWormMinVal;
		this.alarmWormMaxVal = alarmWormMaxVal;
		this.alarmEmerMinVal = alarmEmerMinVal;
		this.alarmEmerMaxVal = alarmEmerMaxVal;
		this.useYn = useYn;
		this.dataCnt = dataCnt;
		this.mappingKey = mappingKey;
		this.assetNm = assetNm;
		this.modelConfirmYn = modelConfirmYn;
	}
}