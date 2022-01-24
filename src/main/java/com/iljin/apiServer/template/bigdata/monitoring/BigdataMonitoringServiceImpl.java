package com.iljin.apiServer.template.bigdata.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BigdataMonitoringServiceImpl implements BigdataMonitoringService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<List<Double>> getContTestList(BigdataMonitoringDto moDto) {
		String siteId = moDto.getSiteId();
		String assetId = moDto.getAssetId();
		String detectionIndexCd = moDto.getDetectionIndexCd();
		String stageDivCd = moDto.getStageDivCd();
		String lotNo = moDto.getLotNo();
		Integer stSeq = moDto.getStSeq();
		Integer endSeq = moDto.getEndSeq();

		//[STEP-1] Stage별 Seq 산출
		if(!"10".equals(stageDivCd)){
			Integer stageStSeq = this.getStageStartSeq(siteId, assetId, detectionIndexCd, stageDivCd);
			stSeq += stageStSeq;
			endSeq += stageStSeq;
		}

		//[STEP-2] 개별 기여도 조회
		List<BigdataContDto> testCont = getContTest(siteId, assetId, detectionIndexCd, stageDivCd, lotNo, stSeq, endSeq);

		//[STEP-3] x축 y축 배열 분리
		List<Double> graphX = new ArrayList<>();
		List<Double> graphY = new ArrayList<>();

		for(BigdataContDto c: testCont){
			graphX.add(c.seq);
			graphY.add(c.value);
		}

		//[STEP-4] 리턴
		List<List<Double>> contTest = new ArrayList<>();
		contTest.add(graphX);
		contTest.add(graphY);

		return contTest;
	}

	@Override
	public List<List<Double>> getContMeanList(BigdataMonitoringDto moDto) {
		String siteId = moDto.getSiteId();
		String assetId = moDto.getAssetId();
		String detectionIndexCd = moDto.getDetectionIndexCd();
		String stageDivCd = moDto.getStageDivCd();
		String lotNo = moDto.getLotNo();
		Integer stSeq = moDto.getStSeq();
		Integer endSeq = moDto.getEndSeq();

		//[STEP-1] 평균 기여도 조회
		List<ThContMeanData> thContMeanData1 = getContMean(siteId, assetId, detectionIndexCd, stageDivCd, lotNo, stSeq, endSeq);

		//[STEP-2] Threshold 조회
		List<ThContMeanData> thContMeanData2 = getThContMean(siteId, assetId, detectionIndexCd, stageDivCd, lotNo, stSeq, endSeq);

		//[STEP-3]
		List<Double> cont = new ArrayList<>();
		for(ThContMeanData c : thContMeanData1){
			cont.add(c.factor1);
			cont.add(c.factor2);
			cont.add(c.factor3);
			cont.add(c.factor4);
			cont.add(c.factor5);
		}

		List<Double> thcont = new ArrayList<>();
		for(ThContMeanData c : thContMeanData2){
			thcont.add(c.factor1);
			thcont.add(c.factor2);
			thcont.add(c.factor3);
			thcont.add(c.factor4);
			thcont.add(c.factor5);
		}

		//[STEP-4] 리턴
		List<List<Double>> contTest = new ArrayList<>();
		contTest.add(cont);
		contTest.add(thcont);

		return contTest;
	}

	/**
	 * 개별 기여도 조회
	 * @param siteId
	 * @param assetId
	 * @param detectionIndexCd
	 * @param stageDivCd
	 * @param stSeq
	 * @param endSeq
	 * @return
	 */
	public List<BigdataContDto> getContTest(  String siteId
											, String assetId
											, String detectionIndexCd
											, String stageDivCd
											, String lotNo
											, Integer stSeq
											, Integer endSeq) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT CAST(SEQ AS Double) AS SEQ\n" +
				"       , CASE \n" +
				"            WHEN FACTOR_1 IS NOT NULL THEN FACTOR_1\n" +
				"            WHEN FACTOR_2 IS NOT NULL THEN FACTOR_2\n" +
				"            WHEN FACTOR_3 IS NOT NULL THEN FACTOR_3\n" +
				"            WHEN FACTOR_4 IS NOT NULL THEN FACTOR_4\n" +
				"            WHEN FACTOR_5 IS NOT NULL THEN FACTOR_5\n" +
				"         END AS VAL   \n" +
				"\tFROM (\n" +
				"\t\tSELECT D1.SEQ\n" +
				"\t\t     , CASE WHEN D1.SEQ = 1 THEN D2.FACTOR_1 ELSE NULL END AS FACTOR_1\n" +
				"\t\t     , CASE WHEN D1.SEQ = 2 THEN D2.FACTOR_2 ELSE NULL END AS FACTOR_2\n" +
				"\t\t     , CASE WHEN D1.SEQ = 3 THEN D2.FACTOR_3 ELSE NULL END AS FACTOR_3\n" +
				"\t\t     , CASE WHEN D1.SEQ = 4 THEN D2.FACTOR_4 ELSE NULL END AS FACTOR_4\n" +
				"\t\t     , CASE WHEN D1.SEQ = 5 THEN D2.FACTOR_5 ELSE NULL END AS FACTOR_5\n" +
				"\t\t\tFROM (\n" +
				"\t\t\t\tSELECT 1 AS SEQ\n" +
				"\t\t\t\t UNION ALL\n" +
				"\t\t\t\tSELECT 2 AS SEQ\n" +
				"\t\t\t\t UNION ALL\n" +
				"\t\t\t\tSELECT 3 AS SEQ\n" +
				"\t\t\t\t UNION ALL\n" +
				"\t\t\t\tSELECT 4 AS SEQ\n" +
				"\t\t\t\t UNION ALL\n" +
				"\t\t\t\tSELECT 5 AS SEQ\n" +
				"\t\t\t\t) D1\n" +
				"\t\t\t  , (\n" +
				"\t\t\t\tSELECT ABS(FACTOR_1) AS FACTOR_1 \n" +
				"\t\t\t\t     , ABS(FACTOR_2) AS FACTOR_2\n" +
				"\t\t\t\t     , ABS(FACTOR_3) AS FACTOR_3\n" +
				"\t\t\t\t     , ABS(FACTOR_4) AS FACTOR_4\n" +
				"\t\t\t\t     , ABS(FACTOR_5) AS FACTOR_5\n" +
				"\t\t\t\t  FROM TB_HEAT_MAP_REAL_DATA\n" +
				"\t\t\t\t WHERE SITE_ID = :siteId\n" +
				"\t\t\t\t   AND ASSET_ID = :assetId\n" +
				"\t\t\t\t   AND DETECTION_INDEX_CD = :detectionIndexCd\n" +
				"\t\t\t\t   AND STAGE_DIV_CD = :stageDivCd\n" +
				"\t\t\t\t   AND LOT_NO = :lotNo\n" +
				"\t\t\t\t   AND :stSeq <= SEQ \n" +
				"\t\t\t\t   AND SEQ <= :endSeq\t\n" +
				"\t\t\t  ) D2\n" +
				"\t\t) S1");

		Query query = entityManager.createNativeQuery(sb.toString());

		query.setParameter("siteId", siteId);
		query.setParameter("assetId", assetId);
		query.setParameter("detectionIndexCd", detectionIndexCd);
		query.setParameter("stageDivCd", stageDivCd);
		query.setParameter("lotNo", lotNo);
		query.setParameter("stSeq", stSeq);
		query.setParameter("endSeq", endSeq);

		return new JpaResultMapper().list(query, BigdataContDto.class);
	}

	/**
	 * 평균 기여도 조회
	 * @param siteId
	 * @param assetId
	 * @param detectionIndexCd
	 * @param stageDivCd
	 * @param stSeq
	 * @param endSeq
	 * @return 
	 */
	public List<ThContMeanData> getContMean(  String siteId
											, String assetId
											, String detectionIndexCd
											, String stageDivCd
											, String lotNo
											, Integer stSeq
											, Integer endSeq) {

		StringBuilder sb = new StringBuilder();
		sb.append("   SELECT AVG(FACTOR_1) AS AVG_FACTOR_1\n" +
				"        , AVG(FACTOR_2) AS AVG_FACTOR_2\n" +
				"        , AVG(FACTOR_3) AS AVG_FACTOR_3\n" +
				"        , AVG(FACTOR_4) AS AVG_FACTOR_4\n" +
				"        , AVG(FACTOR_5) AS AVG_FACTOR_5\n" +
				" \tFROM (\n" +
				" \t\tSELECT SITE_ID \n" +
				" \t\t     , ASSET_ID\n" +
				" \t\t     , DETECTION_INDEX_CD\n" +
				" \t\t     , STAGE_DIV_CD\n" +
				" \t\t     , ABS(FACTOR_1) AS FACTOR_1 \n" +
				" \t\t     , ABS(FACTOR_2) AS FACTOR_2\n" +
				" \t\t     , ABS(FACTOR_3) AS FACTOR_3\n" +
				" \t\t     , ABS(FACTOR_4) AS FACTOR_4\n" +
				" \t\t     , ABS(FACTOR_5) AS FACTOR_5\n" +
				" \t\t  FROM TB_HEAT_MAP_REAL_DATA\n" +
				" \t\t WHERE SITE_ID = :siteId\n" +
				" \t\t   AND ASSET_ID = :assetId\n" +
				" \t\t   AND DETECTION_INDEX_CD = :detectionIndexCd\n" +
				" \t\t   AND STAGE_DIV_CD = :stageDivCd\n" +
				" \t\t   AND LOT_NO = :lotNo\n" +
				" \t\t   AND :stSeq <= SEQ \n" +
				" \t\t   AND SEQ <= :endSeq \n" +
				" \t\t ) D1\n" +
				"    GROUP BY SITE_ID, ASSET_ID, STAGE_DIV_CD, DETECTION_INDEX_CD\t"
		);

		Query query = entityManager.createNativeQuery(sb.toString());

		query.setParameter("siteId", siteId);
		query.setParameter("assetId", assetId);
		query.setParameter("detectionIndexCd", detectionIndexCd);
		query.setParameter("stageDivCd", stageDivCd);
		query.setParameter("lotNo", lotNo);
		query.setParameter("stSeq", stSeq);
		query.setParameter("endSeq", endSeq);

		return new JpaResultMapper().list(query, ThContMeanData.class);
	}

	/**
	 * Treshold 조회
	 * @param siteId
	 * @param assetId
	 * @param detectionIndexCd
	 * @param stageDivCd
	 * @param stSeq
	 * @param endSeq
	 * @return
	 */
	public List<ThContMeanData> getThContMean(  String siteId
											  , String assetId
											  , String detectionIndexCd
											  , String stageDivCd
											  , String lotNo
											  , Integer stSeq
											  , Integer endSeq) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT AVG(FACTOR_1) AS AVG_FACTOR_1\n" +
				"    , AVG(FACTOR_2) AS AVG_FACTOR_2\n" +
				"    , AVG(FACTOR_3) AS AVG_FACTOR_3\n" +
				"    , AVG(FACTOR_4) AS AVG_FACTOR_4\n" +
				"    , AVG(FACTOR_5) AS AVG_FACTOR_5\n" +
				"FROM (\n" +
				"\tSELECT SITE_ID \n" +
				"\t     , ASSET_ID\n" +
				"\t     , DETECTION_INDEX_CD\n" +
				"\t     , STAGE_DIV_CD\n" +
				"\t     , ABS(FACTOR_1) AS FACTOR_1 \n" +
				"\t     , ABS(FACTOR_2) AS FACTOR_2\n" +
				"\t     , ABS(FACTOR_3) AS FACTOR_3\n" +
				"\t     , ABS(FACTOR_4) AS FACTOR_4\n" +
				"\t     , ABS(FACTOR_5) AS FACTOR_5\n" +
				"\t  FROM TB_TH_CONT_MEAN_DATA\n" +
				"\t WHERE SITE_ID = :siteId\n" +
				"\t   AND ASSET_ID = :assetId\n" +
				"\t   AND DETECTION_INDEX_CD = :detectionIndexCd\n" +
				"\t   AND STAGE_DIV_CD = :stageDivCd\n" +
				"\t   AND :stSeq <= SEQ \n" +
				"\t   AND SEQ <= :endSeq \n" +
				"\t ) D1\n" +
				"GROUP BY SITE_ID, ASSET_ID, STAGE_DIV_CD, DETECTION_INDEX_CD"
		);

		Query query = entityManager.createNativeQuery(sb.toString());

		query.setParameter("siteId", siteId);
		query.setParameter("assetId", assetId);
		query.setParameter("detectionIndexCd", detectionIndexCd);
		query.setParameter("stageDivCd", stageDivCd);
		query.setParameter("stSeq", stSeq);
		query.setParameter("endSeq", endSeq);

		return new JpaResultMapper().list(query, ThContMeanData.class);
	}

	/**
	 * 스테이지별 시작 SEQ 값
	 * @param siteId
	 * @param assetId
	 * @param detectionIndexCd
	 * @param stageDivCd
	 * @return
	 */
	public Integer getStageStartSeq(String siteId
			, String assetId
			, String detectionIndexCd
			, String stageDivCd
			) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT MIN(SEQ) - 1 \n" +
				"\t  FROM TB_HEAT_MAP_REAL_DATA\n" +
				"\t WHERE SITE_ID = :siteId\n" +
				"\t   AND ASSET_ID = :assetId\n" +
				"\t   AND DETECTION_INDEX_CD = :detectionIndexCd\n" +
				"\t   AND STAGE_DIV_CD = :stageDivCd\n"
		);

		Query query = entityManager.createNativeQuery(sb.toString());

		query.setParameter("siteId", siteId);
		query.setParameter("assetId", assetId);
		query.setParameter("detectionIndexCd", detectionIndexCd);
		query.setParameter("stageDivCd", stageDivCd);

		return ((Number)query.getSingleResult()).intValue();
	}
}
