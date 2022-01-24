package com.iljin.apiServer.template.asset.chartdata;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@IdClass(PCAChartKey.class)
@Table(name = "tb_pca_cpv_data")
public class PCAChart {
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
    @Column(name="var_no")
    String varNo;

    @Column(name="eigenvalue")
    Double eigenvalue;

    @Column(name="cpv")
    Double cpv;

    @Column(name="th_cpv")
    Double thCpv;

    @Column(name="create_date_time")
    LocalDateTime createDateTime;
}
