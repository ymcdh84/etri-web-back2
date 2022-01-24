package com.iljin.apiServer.template.asset;

import com.iljin.apiServer.template.asset.chartdata.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService{
    private final AssetRepository assetRepository;
    private final AssetRepositoryCustom assetRepositoryCustom;
    private final PredictChartRepository predictChartRepository;
    private final RealTimeChartRepository realTimeChartRepository;
    private final PCAChartRepository pcaChartRepository;
    private final SPEChartRepository speChartRepository;

    @Override
    public List<AssetHeaderDto> getAssetHeader(String assetId) {
        List<AssetHeaderDto> list = assetRepository.getAssetHeader(assetId)
                .stream()
                .map(s -> new AssetHeaderDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                )).collect(Collectors.toList());

        return list;
    }

    @Override
    public List<AssetDetailDto> getAssetDetailInfo(String assetId) {
        return assetRepositoryCustom.getAssetDetailInfo(assetId);
    }

    @Override
    public List<AssetLastInfoDto> getAssetLastInfo(String assetId){

        List<AssetLastInfoDto> list = assetRepository.getAssetLastInfo(assetId)
                .stream()
                .map(s -> new AssetLastInfoDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[5]).orElse(""))
                )).collect(Collectors.toList());

        return list;

    }

    @Override
    public List<PredictChart> getPredictData(String assetId, String stageDivCd, String predictDivCd ) {
        return predictChartRepository.findByAssetIdContainsAndStageDivCdContainsAndPredictDivCdContains(assetId, stageDivCd, predictDivCd);
    }

    @Override
    public List<RealTimeChart> getRealTimeData(String assetId, String stageDivCd) {
        return realTimeChartRepository.getRealTimeData(assetId, stageDivCd);
    }

    @Override
    public Map<String, Object> getStageLevel(String assetId) {
        return realTimeChartRepository.getStageLevel(assetId);
    }

    @Override
    public Map<String, Object> getPcaData(String assetId, String stageDivCd) {
        Map<String, Object> map = new HashMap<>();
        List<String> varNo = new ArrayList<>();
        List<Double> eigenvalue = new ArrayList<>();
        List<Double> cpv = new ArrayList<>();
        List<Double> thCpv = new ArrayList<>();

        ArrayList<ArrayList> realData = (ArrayList)pcaChartRepository.findByAssetIdContainsAndStageDivCdContains(assetId,stageDivCd);
        for(ArrayList list : realData){

            String ivarNo = (String)list.get(0);
            double ieigenvalue = (double)list.get(1);
            double icpv = (double)list.get(2);
            double ithCpv = (double)list.get(3);

            ivarNo = "No"+ivarNo;

            varNo.add(ivarNo);
            eigenvalue.add(ieigenvalue);
            cpv.add(icpv);
            thCpv.add(ithCpv);
        }
        map.put("varNo",varNo);
        map.put("eigenvalue",eigenvalue);
        map.put("cpv",cpv);
        map.put("thCpv",thCpv);
        return map;
    }

    @Override
    public Map<String, Object> getHistogramData(String assetId, String detectionIndexCd, String stageDivCd) {
        Map<String, Object> map = new HashMap<>();
        List<Double> val = new ArrayList<>();
        List<Double> thVal = new ArrayList<>();

        ArrayList<ArrayList> realData = (ArrayList)pcaChartRepository.getHistogramData(assetId, detectionIndexCd, stageDivCd);
        for(ArrayList list : realData){

            double ival = (double)list.get(0);
            double ithVal = (double)list.get(1);

            val.add(ival);
            thVal.add(ithVal);
        }
        map.put("val",val);
        map.put("thVal",thVal);
        return map;
    }

    @Override
    public Map<String, Object> getSpeData(String assetId, String detectionIndexCd, String stageDivCd) {
        Map<String, Object> map = new HashMap<>();
        List<Double> theSpe = new ArrayList<>();

        ArrayList<ArrayList> realData = (ArrayList)speChartRepository.getSpeData(assetId, detectionIndexCd, stageDivCd);

        for(ArrayList list : realData){

            double itheSpe = (double)list.get(0);

            theSpe.add(itheSpe);
        }

        map.put("theSpe",theSpe);

        return map;
    }

    @Override
    public Map<String, Object> getSpeRealData(String assetId, String detectionIndexCd, String stageDivCd, String lotNo) {
        Map<String, Object> map = new HashMap<>();
        List<Double> speTest = new ArrayList<>();

        ArrayList<ArrayList> realData = (ArrayList)speChartRepository.getSpeRealData(assetId, detectionIndexCd, stageDivCd, lotNo);
        for(ArrayList list : realData){

            double ispeTest = (double)list.get(0);

            speTest.add(ispeTest);
        }
        map.put("speTest",speTest);
        return map;
    }

    @Override
    public Map<String, Object> getHeatMapData(String assetId, String detectionIndexCd, String stageDivCd, String lotNo) {
        Map<String, Object> map = new HashMap<>();
        List<Double> factor1 = new ArrayList<>();
        List<Double> factor2 = new ArrayList<>();
        List<Double> factor3 = new ArrayList<>();
        List<Double> factor4 = new ArrayList<>();
        List<Double> factor5 = new ArrayList<>();

        ArrayList<ArrayList> realData = (ArrayList)speChartRepository.getHeatMapData(assetId, detectionIndexCd, stageDivCd, lotNo);
        for(ArrayList list : realData){

            double ifactor1 = (double)list.get(0);
            double ifactor2 = (double)list.get(1);
            double ifactor3 = (double)list.get(2);
            double ifactor4 = (double)list.get(3);
            double ifactor5 = (double)list.get(4);

            factor1.add(ifactor1);
            factor2.add(ifactor2);
            factor3.add(ifactor3);
            factor4.add(ifactor4);
            factor5.add(ifactor5);
        }
        map.put("factor1",factor1);
        map.put("factor2",factor2);
        map.put("factor3",factor3);
        map.put("factor4",factor4);
        map.put("factor5",factor5);
        return map;
    }


    @Override
    public Map<String, Object> getRealTimeData1(String assetId, String stageDivCd, String lotNo){
        Map<String, Object> map = new HashMap<>();
        List<Double> factor1 = new ArrayList<>();
        List<Double> factor2 = new ArrayList<>();
        List<Double> factor3 = new ArrayList<>();
        List<Double> factor4 = new ArrayList<>();
        List<Double> factor5 = new ArrayList<>();
        List<Double> factor6 = new ArrayList<>();
        List<Double> factor7 = new ArrayList<>();
        List<Double> factor8 = new ArrayList<>();
        List<Double> factor9 = new ArrayList<>();

        ArrayList<ArrayList> realData = (ArrayList)realTimeChartRepository.getRealTimeData1(assetId, stageDivCd, lotNo);
        for(ArrayList list : realData){

            double ifactor1 = (double)list.get(0);
            double ifactor2 = (double)list.get(1);
            double ifactor3 = (double)list.get(2);
            double ifactor4 = (double)list.get(3);
            double ifactor5 = (double)list.get(4);
            double ifactor6 = (double)list.get(5);
            double ifactor7 = (double)list.get(6);
            double ifactor8 = (double)list.get(7);
            double ifactor9 = (double)list.get(8);

            factor1.add(ifactor1);
            factor2.add(ifactor2);
            factor3.add(ifactor3);
            factor4.add(ifactor4);
            factor5.add(ifactor5);
            factor6.add(ifactor6);
            factor7.add(ifactor7);
            factor8.add(ifactor8);
            factor9.add(ifactor9);
        }
        map.put("factor1",factor1);
        map.put("factor2",factor2);
        map.put("factor3",factor3);
        map.put("factor4",factor4);
        map.put("factor5",factor5);
        map.put("factor6",factor6);
        map.put("factor7",factor7);
        map.put("factor8",factor8);
        map.put("factor9",factor9);

        return map;
    }

    @Override
    public Map<String, Object> getPredictData1(String assetId, String stageDivCd, String predictDivCd ){
        Map<String, Object> map = new HashMap<>();
        List<Double> factor1 = new ArrayList<>();
        List<Double> factor2 = new ArrayList<>();
        List<Double> factor3 = new ArrayList<>();
        List<Double> factor4 = new ArrayList<>();
        List<Double> factor5 = new ArrayList<>();
        List<Double> factor6 = new ArrayList<>();
        List<Double> factor7 = new ArrayList<>();
        List<Double> factor8 = new ArrayList<>();
        List<Double> factor9 = new ArrayList<>();

        ArrayList<ArrayList> realData = (ArrayList)predictChartRepository.getPredictData1(assetId, stageDivCd, predictDivCd);
        for(ArrayList list : realData){

            double ifactor1 = (double)list.get(0);
            double ifactor2 = (double)list.get(1);
            double ifactor3 = (double)list.get(2);
            double ifactor4 = (double)list.get(3);
            double ifactor5 = (double)list.get(4);
            double ifactor6 = (double)list.get(5);
            double ifactor7 = (double)list.get(6);
            double ifactor8 = (double)list.get(7);
            double ifactor9 = (double)list.get(8);

            factor1.add(ifactor1);
            factor2.add(ifactor2);
            factor3.add(ifactor3);
            factor4.add(ifactor4);
            factor5.add(ifactor5);
            factor6.add(ifactor6);
            factor7.add(ifactor7);
            factor8.add(ifactor8);
            factor9.add(ifactor9);
        }
        map.put("factor1",factor1);
        map.put("factor2",factor2);
        map.put("factor3",factor3);
        map.put("factor4",factor4);
        map.put("factor5",factor5);
        map.put("factor6",factor6);
        map.put("factor7",factor7);
        map.put("factor8",factor8);
        map.put("factor9",factor9);

        return map;
    }

}
