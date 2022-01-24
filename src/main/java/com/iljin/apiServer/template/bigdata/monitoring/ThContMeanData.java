package com.iljin.apiServer.template.bigdata.monitoring;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_th_cont_mean_data")
public class ThContMeanData implements Serializable {

	@Id
	@Column(name="site_id", nullable=false)
	String siteId;

	@Id
	@Column(name = "asset_id", nullable=false)
	String assetId;

	@Id
	@Column(name = "detection_index_cd", nullable=false)
	String detectionIndexCd;

	@Id
	@Column(name = "stage_div_cd", nullable=false)
	String stageDivCd;

	@Id
	@Column(name = "seq", nullable=false)
	Integer seq;

	@Column(name = "factor_1")
	Double factor1;

	@Column(name = "factor_2")
	Double factor2;

	@Column(name = "factor_3")
	Double factor3;

	@Column(name = "factor_4")
	Double factor4;

	@Column(name = "factor_5")
	Double factor5;

	@Column(name = "factor_6")
	Double factor6;

	@Column(name = "factor_7")
	Double factor7;

	@Column(name = "factor_8")
	Double factor8;

	@Column(name = "factor_9")
	Double factor9;

	@Column(name = "factor_10")
	Double factor10;

	@Column(name = "factor_11")
	Double factor11;

	@Column(name = "factor_12")
	Double factor12;

	@Column(name = "factor_13")
	Double factor13;

	@Column(name = "factor_14")
	Double factor14;

	@Column(name = "factor_15")
	Double factor15;

	@Column(name = "factor_16")
	Double factor16;

	@Column(name = "factor_17")
	Double factor17;

	@Column(name = "factor_18")
	Double factor18;

	@Column(name = "factor_19")
	Double factor19;

	@Column(name = "factor_20")
	Double factor20;

	@Column(name = "create_date_time")
	LocalDateTime createDateTime;

	@Builder
	public ThContMeanData(
			Double factor1
			, Double factor2
			, Double factor3
			, Double factor4
			, Double factor5
	) {
		this.factor1 = factor1;
		this.factor2 = factor2;
		this.factor3 = factor3;
		this.factor4 = factor4;
		this.factor5 = factor5;
	}
}
