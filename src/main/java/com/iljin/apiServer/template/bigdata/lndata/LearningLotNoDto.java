package com.iljin.apiServer.template.bigdata.lndata;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LearningLotNoDto {

	String siteId;
	String siteNm;
	String assetId;
	String assetNm;
	String lotNo;
	Integer dataCnt;
	String learningYn;
	String learningYnBefore;
	String pcaBatchDateTime;
	String predictBatchDateTime;
	String createDateTime;

	/*
	 * 학습 데이터 조회
	 * */
	public LearningLotNoDto(String siteId
			, String siteNm
			, String assetId
			, String assetNm
			, String lotNo
 		    , Integer dataCnt
			, String learningYn
			, String learningYnBefore
			, String pcaBatchDateTime
			, String predictBatchDateTime
			, String createDateTime
	) {
		this.siteId = siteId;
		this.siteNm = siteNm;
		this.assetId = assetId;
		this.assetNm = assetNm;
		this.lotNo = lotNo;
		this.dataCnt = dataCnt;
		this.learningYn = learningYn;
		this.learningYnBefore = learningYnBefore;
		this.pcaBatchDateTime = pcaBatchDateTime;
		this.predictBatchDateTime = predictBatchDateTime;
		this.createDateTime = createDateTime;
	}
}
