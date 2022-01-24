package com.iljin.apiServer.template.asset.chartdata;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PredictChartKey implements Serializable {
    String siteId;
    String assetId;
    String predictDivCd;
    String stageDivCd;
    Integer seq;
}
