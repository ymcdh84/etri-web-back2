package com.iljin.apiServer.template.bigdata.site;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_bigdata_site")
public class BigdataSite {

	@Id
	@Column(name="site_id", nullable=false)
	String siteId;

	@Column(name = "site_nm")
	String siteNm;

	@Column(name = "site_desc")
	String siteDesc;

	@Builder
	public BigdataSite(String siteId, String siteNm, String siteDesc) {
		this.siteId = siteId;
		this.siteNm = siteNm;
		this.siteDesc = siteDesc;
	}

	@Builder
	public BigdataSite(String siteId) {
		this.siteId = siteId;
	}

	public BigdataSite updateBigdataSite(String siteNm, String siteDesc) {
		this.siteNm = siteNm;
		this.siteDesc = siteDesc;
		return this;
	}
}
