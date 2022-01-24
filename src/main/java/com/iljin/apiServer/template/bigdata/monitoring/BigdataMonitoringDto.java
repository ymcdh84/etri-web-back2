package com.iljin.apiServer.template.bigdata.monitoring;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class BigdataMonitoringDto implements Serializable {
	String siteId;
	String assetId;
	String detectionIndexCd;
	String stageDivCd;
	String lotNo;
	Integer stSeq;
	Integer endSeq;
}
