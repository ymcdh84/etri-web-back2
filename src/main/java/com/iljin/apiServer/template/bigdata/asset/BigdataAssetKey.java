package com.iljin.apiServer.template.bigdata.asset;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BigdataAssetKey implements Serializable {
	String siteId;
	String assetId;

	@Builder
	public BigdataAssetKey(String siteId, String assetId) {
		this.siteId = siteId;
		this.assetId = assetId;
	}

	/* Default Constructor */
	public BigdataAssetKey() {}
}
