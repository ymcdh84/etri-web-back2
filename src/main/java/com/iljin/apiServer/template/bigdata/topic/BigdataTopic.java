package com.iljin.apiServer.template.bigdata.topic;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_bigdata_topic")
@IdClass(BigdataTopicKey.class)
public class BigdataTopic  implements Serializable {

	@Id
	@Column(name="site_id", nullable=false)
	String siteId;

	@Id
	@Column(name = "connect_id")
	String connectId;

	@Id
	@Column(name = "topic_id")
	String topicId;

	@Column(name = "topic_desc")
	String topicDesc;

	@Column(name = "submission_id")
	String submissionId;

	@Column(name = "submission_stat_cd")
	String submissionStatCd;

	@Builder
	public BigdataTopic(String siteId, String connectId, String topicId, String topicDesc, String submissionId, String submissionStatCd) {
		this.siteId = siteId;
		this.connectId = connectId;
		this.topicId = topicId;
		this.topicDesc = topicDesc;
		this.submissionId = submissionId;
		this.submissionStatCd = submissionStatCd;
	}

	public BigdataTopic updateBigdataTopic(String topicId, String topicDesc, String submissionId, String submissionStatCd) {
		this.topicId = topicId;
		this.topicDesc = topicDesc;
		this.submissionId = submissionId;
		this.submissionStatCd = submissionStatCd;

		return this;
	}

	public BigdataTopic updateBigdataTopicStatCd(String submissionStatCd) {
		this.submissionStatCd = submissionStatCd;
		return this;
	}
}
