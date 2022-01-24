package com.iljin.apiServer.template.bigdata.connect;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BigdataConnectKey implements Serializable {
	String siteId;
	String connectId;

	@Builder
	public BigdataConnectKey(String siteId, String connectId) {
		this.siteId = siteId;
		this.connectId = connectId;
	}

	/* Default Constructor */
	public BigdataConnectKey() {}
}
