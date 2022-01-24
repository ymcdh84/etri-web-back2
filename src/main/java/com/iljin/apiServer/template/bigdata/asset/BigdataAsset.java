package com.iljin.apiServer.template.bigdata.asset;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NamedStoredProcedureQuery(
		name = "SP_BIGDATA_ASSET_MODELING",
		procedureName = "SP_BIGDATA_ASSET_MODELING",
		parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "pSITE_ID"),
				@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "pASSET_ID"),
				@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "oRtnCd"),
				@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "oRtnMsg")
		}
)

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_bigdata_asset")
@IdClass(BigdataAssetKey.class)
public class BigdataAsset {

	@Id
	@Column(name="site_id", nullable=false)
	String siteId;

	@Id
	@Column(name = "asset_id", nullable=false)
	String assetId;

	@Column(name = "asset_nm")
	String assetNm;

	@Column(name = "asset_group")
	Integer assetGroup;

	@Column(name = "up_asset_id")
	String upAssetId;

	@Column(name = "asset_desc")
	String assetDesc;

	@Column(name = "asset_stat_cd")
	String assetStatCd;

	@Column(name = "model_confirm_yn")
	String modelConfirmYn;

	@Builder
	public BigdataAsset(String siteId
			, String assetId
			, String assetNm
			, Integer assetGroup
			, String upAssetId
			, String assetDesc
			, String assetStatCd
			, String modelConfirmYn
	) {
		this.siteId = siteId;
		this.assetId = assetId;
		this.assetNm = assetNm;
		this.assetGroup = assetGroup;
		this.upAssetId = upAssetId;
		this.assetDesc = assetDesc;
		this.assetStatCd = assetStatCd;
		this.modelConfirmYn = modelConfirmYn;
	}

	public BigdataAsset updateAsset(String assetNm
			, Integer assetGroup
			, String upAssetId
			, String assetDesc
			, String assetStatCd
			, String modelConfirmYn
	) {
		this.assetNm = assetNm;
		this.assetGroup = assetGroup;
		this.upAssetId = upAssetId;
		this.upAssetId = upAssetId;
		this.assetDesc = assetDesc;
		this.assetStatCd = assetStatCd;
		this.modelConfirmYn = modelConfirmYn;

		return this;
	}
}
