package com.iljin.apiServer.template.bigdata.tag;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BigdataAssetTagKey implements Serializable {
	String siteId;
	String assetId;
	String tagId;

	@Builder
	public BigdataAssetTagKey(String siteId, String assetId, String tagId) {
		this.siteId = siteId;
		this.assetId = assetId;
		this.tagId = tagId;
	}

	/* Default Constructor */
	public BigdataAssetTagKey() {}
}
