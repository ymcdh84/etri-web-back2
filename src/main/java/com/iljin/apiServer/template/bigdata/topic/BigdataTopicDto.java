package com.iljin.apiServer.template.bigdata.topic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class BigdataTopicDto implements Serializable {
	String siteId;
	String connectId;
	String topicId;	
	String topicDesc;	
	String submissionId;	
	String submissionStatCd;
	
	/*
	* 토픽 조회
	* */
	public BigdataTopicDto(String siteId
							, String connectId
							, String topicId
							, String topicDesc
							, String submissionId
							, String submissionStatCd
							)
	{
		this.siteId = siteId;
		this.connectId = connectId;
		this.topicId = topicId;
		this.topicDesc = topicDesc;
		this.submissionId = submissionId;
		this.submissionStatCd = submissionStatCd;
	}

}

