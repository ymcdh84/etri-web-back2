package com.iljin.apiServer.template.bigdata.tag;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor

@Setter
@Getter
@Entity
@Table(name = "tb_bigdata_asset_tag")
@IdClass(BigdataAssetTagKey.class)
public class AssetTag {

	@Id
	@Column(name="site_id", nullable=false)
	String siteId;

	@Id
	@Column(name = "asset_id", nullable=false)
	String assetId;

	@Id
	@Column(name = "tag_id", nullable=false)
	String tagId;

	@Column(name = "tag_nm")
	String tagNm;

	@Column(name = "tag_desc")
	String tagDesc;

	@Column(name = "data_type_cd")
	String dataTypeCd;

	@Column(name = "cycle")
	Integer cycle;

	@Column(name = "importance_cd")
	String importanceCd;

	@Column(name = "alias")
	String alias;

	@Column(name = "input_min_val")
	Integer inputMinVal;

	@Column(name = "input_max_val")
	Integer inputMaxVal;

	@Column(name = "format")
	String format;

	@Column(name = "alarm_info_min_val")
	Double alarmInfoMinVal;

	@Column(name = "alarm_info_max_val")
	Double alarmInfoMaxVal;

	@Column(name = "alarm_worm_min_val")
	Double alarmWormMinVal;

	@Column(name = "alarm_worm_max_val")
	Double alarmWormMaxVal;

	@Column(name = "alarm_emer_min_val")
	Double alarmEmerMinVal;

	@Column(name = "alarm_emer_max_val")
	Double alarmEmerMaxVal;

	@Column(name = "use_yn")
	String useYn;

	@Column(name = "data_cnt")
	Integer dataCnt;

	@Column(name = "mapping_key")
	String mappingKey;

	@Builder
	public AssetTag(String siteId
			, String assetId
			, String tagId
			, String tagNm
			, String dataTypeCd
			, String mappingKey
	) {
		this.siteId = siteId;
		this.assetId = assetId;
		this.tagId = tagId;
		this.tagNm = tagNm;
		this.dataTypeCd = dataTypeCd;
		this.mappingKey = mappingKey;
	}

	public AssetTag updateAssetTag(String tagNm
			, String tagDesc
			, String dataTypeCd
			, Integer cycle
			, String importanceCd
			, String alias
			, Integer inputMinVal
			, Integer inputMaxVal
			, String format
			, Double alarmInfoMinVal
			, Double alarmInfoMaxVal
			, Double alarmWormMinVal
			, Double alarmWormMaxVal
			, Double alarmEmerMinVal
			, Double alarmEmerMaxVal
			, String useYn
			, Integer dataCnt
			, String mappingKey
	) {
		this.tagNm = tagNm;
		this.tagDesc = tagDesc;
		this.dataTypeCd = dataTypeCd;
		this.cycle = cycle;
		this.importanceCd = importanceCd;
		this.alias = alias;
		this.inputMinVal = inputMinVal;
		this.inputMaxVal = inputMaxVal;
		this.format = format;
		this.alarmInfoMinVal = alarmInfoMinVal;
		this.alarmInfoMaxVal = alarmInfoMaxVal;
		this.alarmWormMinVal = alarmWormMinVal;
		this.alarmWormMaxVal = alarmWormMaxVal;
		this.alarmEmerMinVal = alarmEmerMinVal;
		this.alarmEmerMaxVal = alarmEmerMaxVal;
		this.useYn = useYn;
		this.dataCnt = dataCnt;
		this.mappingKey = mappingKey;

		return this;
	}
}
