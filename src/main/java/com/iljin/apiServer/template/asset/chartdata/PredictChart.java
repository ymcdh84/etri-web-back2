package com.iljin.apiServer.template.asset.chartdata;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@IdClass(PredictChartKey.class)
@Table(name = "tb_predict_chart_data")
public class PredictChart {
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
    @Column(name="predict_div_cd")
    String predictDivCd;

    @Id
    @Column(name="seq")
    Integer seq;

    @Column(name="factor_1")
    Double factor1;

    @Column(name="factor_2")
    Double factor2;

    @Column(name="factor_3")
    Double factor3;

    @Column(name="factor_4")
    Double factor4;

    @Column(name="factor_5")
    Double factor5;

    @Column(name="factor_6")
    Double factor6;

    @Column(name="factor_7")
    Double factor7;

    @Column(name="factor_8")
    Double factor8;

    @Column(name="factor_9")
    Double factor9;

    @Column(name="factor_10")
    Double factor10;

    @Column(name="factor_11")
    Double factor11;

    @Column(name="factor_12")
    Double factor12;

    @Column(name="factor_13")
    Double factor13;

    @Column(name="factor_14")
    Double factor14;

    @Column(name="factor_15")
    Double factor15;

    @Column(name="factor_16")
    Double factor16;

    @Column(name="factor_17")
    Double factor17;

    @Column(name="factor_18")
    Double factor18;

    @Column(name="factor_19")
    Double factor19;

    @Column(name="factor_20")
    Double factor20;

    @Column(name="create_date_time")
    LocalDateTime createDateTime;

}
