package com.iljin.apiServer.template.bigdata.site;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class BigdataSiteDto implements Serializable {
	String siteId;
	String siteNm;
	String siteDesc;

	/*
	* 사이트 조회
	* */
	public BigdataSiteDto(String siteId, String siteNm, String siteDesc) {
		this.siteId = siteId;
		this.siteNm = siteNm;
		this.siteDesc = siteDesc;
	}

}
