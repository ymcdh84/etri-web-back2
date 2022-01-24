package com.iljin.apiServer.template.bigdata.topic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BigdataTopicKey implements Serializable {
	String siteId;
	String connectId;
	String topicId;

	@Builder
	public BigdataTopicKey(String siteId, String connectId, String topicId) {
		this.siteId = siteId;
		this.connectId = connectId;
		this.topicId = topicId;
	}

	/* Default Constructor */
	public BigdataTopicKey() {}
}
