package com.iljin.apiServer.template.asset;

import com.iljin.apiServer.template.asset.chartdata.PredictChart;
import com.iljin.apiServer.template.asset.chartdata.RealTimeChart;

import java.util.List;
import java.util.Map;

public interface AssetService {
    List<AssetHeaderDto> getAssetHeader(String assetId);

    List<AssetDetailDto> getAssetDetailInfo(String assetId);

    List<AssetLastInfoDto> getAssetLastInfo(String assetId);

    List<PredictChart> getPredictData(String assetId, String stageDivCd,String predictDivCd);

    List<RealTimeChart>getRealTimeData(String assetId, String stageDivCd);

    Map<String, Object> getStageLevel(String assetId);

    Map<String, Object>getPcaData(String assetId, String stageDivCd);

    Map<String, Object>getSpeData(String assetId, String detectionIndexCd, String stageDivCd);

    Map<String, Object>getSpeRealData(String assetId, String detectionIndexCd, String stageDivCd, String lotNo);

    Map<String, Object>getRealTimeData1(String assetId, String stageDivCd, String lotNo);

    Map<String, Object>getPredictData1(String assetId, String stageDivCd,String predictDivCd);

    Map<String, Object>getHeatMapData(String assetId, String detectionIndexCd, String stageDivCd, String lotNo);

    Map<String, Object>getHistogramData(String assetId, String detectionIndexCd, String stageDivCd);
}
