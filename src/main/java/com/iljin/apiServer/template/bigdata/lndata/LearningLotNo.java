package com.iljin.apiServer.template.bigdata.lndata;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor

@Setter
@Getter
@Entity
@Table(name = "tb_learning_lot_no")
@IdClass(LearningLotNoKey.class)
public class LearningLotNo {

	@Id
	@Column(name="site_id", nullable=false)
	String siteId;

	@Id
	@Column(name = "asset_id", nullable=false)
	String assetId;

	@Id
	@Column(name = "lot_no", nullable=false)
	String lotNo;

	@Column(name = "data_cnt")
	Integer dataCnt;

	@Column(name = "create_date_time")
	LocalDateTime createDateTime;

	@Builder
	public LearningLotNo(String siteId, String assetId, String lotNo, Integer dataCnt, LocalDateTime createDateTime) {
		this.siteId = siteId;
		this.assetId = assetId;
		this.lotNo = lotNo;
		this.dataCnt = dataCnt;
		this.createDateTime = createDateTime;
	}
}
