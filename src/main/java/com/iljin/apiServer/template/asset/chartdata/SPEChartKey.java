package com.iljin.apiServer.template.asset.chartdata;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SPEChartKey implements Serializable {

    String siteId;
    String assetId;
    String stageDivCd;
    String lotNo;
    Integer seq;
}
