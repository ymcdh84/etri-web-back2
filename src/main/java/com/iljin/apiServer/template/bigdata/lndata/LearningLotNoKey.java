package com.iljin.apiServer.template.bigdata.lndata;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LearningLotNoKey implements Serializable {
	String siteId;
	String assetId;
	String lotNo;

	@Builder
	public LearningLotNoKey(String siteId, String assetId, String lotNo) {
		this.siteId = siteId;
		this.assetId = assetId;
		this.lotNo = lotNo;
	}

	/* Default Constructor */
	public LearningLotNoKey() {}
}
