package com.iljin.apiServer.template.asset.chartdata;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@IdClass(SPEChartKey.class)
@Table(name = "tb_pca_spe_data")
public class SPEChart {
    @Id
    @Column(name="site_id")
    String siteId;

    @Id
    @Column(name="asset_id")
    String assetId;

    @Id
    @Column(name="stage_div_cd")
    String stageDivCd;

    @Id
    @Column(name="lot_no")
    String lotNo;

    @Id
    @Column(name="seq")
    Integer seq;

    @Column(name="the_spe")
    Double theSpe;

    @Column(name="spe_test")
    Double speTest;

    @Column(name="create_date_time")
    LocalDateTime createDateTime;
}
