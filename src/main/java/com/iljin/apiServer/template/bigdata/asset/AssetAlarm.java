package com.iljin.apiServer.template.bigdata.asset;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_bigdata_alarm_history")
public class AssetAlarm {

	@Id
	@Column(name="alarm_id", nullable=false)
	String alarmId;

	@Column(name = "site_id")
	String siteId;

	@Column(name = "asset_id")
	String assetId;

	@Column(name = "tag_id")
	String tagId;

	@Column(name = "alarm_date_time")
	LocalDateTime alarmDateTime;

	@Column(name = "alarm_val")
	Double alarmVal;

	@Column(name = "alarm_level_cd")
	String alarmLevelCd;

	@Column(name = "alarm_desc")
	String alarmDesc;

	@Column(name = "importance_cd")
	String importanceCd;

	@Column(name = "confirm_yn")
	String confirmYn;

	public AssetAlarm updateAssetAlarmConfirm(String confirmYn
	) {
		this.confirmYn = confirmYn;
		return this;
	}
}
