package com.iljin.apiServer.template.asset;

import com.iljin.apiServer.template.system.settings.UserDashboard;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@IdClass(AssetHeaderKey.class)
@Table(name="tb_bigdata_asset")
public class AssetHeader {
    @Id
    @Column(name="site_id")
    String siteId;

    @Id
    @Column(name="asset_id")
    String assetId;

    @Column(name="asset_nm")
    String assetNm;

    @Column(name="asset_group")
    String assetGroup;

    @Column(name="asset_desc")
    String assetDesc;

    @Column(name="up_asset_id")
    String upAssetId;

    @Column(name="asset_stat_cd")
    String assetStatCd;

    @Column(name="model_confirm_yn")
    String modelConfirmYn;

    @Column(name="seq_proc_stat_cd")
    String seqProcStatCd;

    @Column(name="grow_proc_stat_cd")
    String growProcStatCd;

    @Column(name="stat_chg_dtm")
    LocalDateTime statChgDtm;

    @Column(name="reg_dtm")
    LocalDateTime regDtm;

    @Column(name="chg_dtm")
    LocalDateTime chgDtm;
}
