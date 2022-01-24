package com.iljin.apiServer.template.bigdata.lndata;

import com.iljin.apiServer.template.bigdata.mongodb.AssetLotNumDto;
import com.iljin.apiServer.template.bigdata.mongodb.MongoDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class LearningDataServiceImpl implements LearningDataService {

	@Autowired
	private MongoDbService mongoDbService;
	
	@PersistenceContext
	private EntityManager entityManager;

	private final LearningLotNoRepository learningLotNoRepository;

	@Override
	public List<LearningLotNoDto> getLearningLotInfoList(LearningLotNoDto learningDto) {

		//[STEP-1] MongoDb내 성장로별 Lot데이터 건수 리스트 조회
		List<AssetLotNumDto> mongoLots =  mongoDbService.getColLotDataCnt();

		//[STEP-2] MES(정상 Lot 데이터), 학습데이터 테이블 조인 리스트 조회
		List<LearningLotNoDto> list = this.getLearningLotData(learningDto);
		
		//[STEP-3] 학습데이터 관리 테이블에 존재 하지 않는 LOT 번호 데이터 건수 세팅
		List<LearningLotNoDto> rtList = new ArrayList<>();

		for(AssetLotNumDto c : mongoLots){
		  String mgAssetId = c.getAssetId();
		  String mgLotNo = c.getLotNo();
		  Integer LotCnt = c.getDataCnt();

		  //Step2 리스트에 존재하는 Lot번호만 필터링 되도록
		  List<LearningLotNoDto> learnTemp = list.stream().filter(a ->
		  	a.lotNo.equals(mgLotNo)
		  ).collect(Collectors.toList());

		  if(learnTemp.size() > 0){
		  	for(LearningLotNoDto s : learnTemp){
		  		if("Y".equals(s.getLearningYn())){
					rtList.add(s);
				}else{
		  			s.setDataCnt(LotCnt);
					rtList.add(s);
				}
			}
		  }
		}

		return rtList;
	}

	@Override
	public List<LearningLotNoDto> getLearningLotData(LearningLotNoDto learningDto) {

		String siteId = learningDto.getSiteId();
		String assetId = learningDto.getAssetId();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT D1.SITE_ID\n" +
				"     , D3.SITE_NM \n" +
				"     , D1.ASSET_ID\n" +
				"     , D4.ASSET_NM\n" +
				"     , D1.LOT_NO\n" +
				"     , D2.DATA_CNT\n" +
				"     , CASE \n" +
				"            WHEN D2.LOT_NO IS NULL THEN 'N'\n" +
				"            ELSE 'Y'\n" +
				"       END AS LEARNING_YN\n" +
				"     , CASE \n" +
				"            WHEN D2.LOT_NO IS NULL THEN 'N'\n" +
				"            ELSE 'Y'\n" +
				"       END AS LEARNING_YN_BEFORE\n" +
				"     , DATE_FORMAT(D2.PCA_BATCH_DATE_TIME, '%Y-%m-%d %T') AS PCA_BATCH_DATE_TIME \n" +
				"     , DATE_FORMAT(D2.PREDICT_BATCH_DATE_TIME, '%Y-%m-%d %T') AS PREDICT_BATCH_DATE_TIME \n" +
				"     , DATE_FORMAT(D2.CREATE_DATE_TIME, '%Y-%m-%d %T') AS CREATE_DATE_TIME \n" +
				"  FROM (\n" +
				"\tSELECT 'SITE_00001' AS SITE_ID \n" +
				"\t     , RES_ID AS ASSET_ID\n" +
				"\t     , LOT_ID AS LOT_NO\n" +
				"\t  FROM INTERFACE.VBOULEEDCYIELD D1     \t     \n" +
				"\t WHERE UNIT = '*'\n" +
				"\t   AND EXISTS (SELECT 1\n" +
				"\t                 FROM V_DATA_EXIST_MODELING_TABLES S1\n" +
				"\t                WHERE S1.ASSET_ID = D1.RES_ID)\n" +
				"\t GROUP BY RES_ID, LOT_ID\n" +
				"     ) D1\n" +
				"     LEFT JOIN TB_LEARNING_LOT_NO D2 ON D2.SITE_ID = D1.SITE_ID AND D2.ASSET_ID = D1.ASSET_ID AND D2.LOT_NO  = D1.LOT_NO     \n" +
				"    INNER JOIN TB_BIGDATA_SITE D3 ON D3.SITE_ID = D1.SITE_ID  \n" +
				"    INNER JOIN TB_BIGDATA_ASSET D4  ON D4.SITE_ID = D1.SITE_ID AND D4.ASSET_ID = D1.ASSET_ID\n" +
				"    WHERE 1 = 1");

		if (!StringUtils.isEmpty(siteId)) {// 사이트ID
			sb.append("   AND (D3.SITE_ID LIKE CONCAT('%', ifnull(:siteId,''), '%')" +
					"        OR D3.SITE_NM LIKE CONCAT('%', ifnull(:siteId,''), '%'))");
		}

		if (!StringUtils.isEmpty(assetId)) {// 자산ID
			sb.append("   AND (D4.ASSET_ID LIKE CONCAT('%', ifnull(:assetId,''), '%')" +
					"        OR D4.ASSET_NM LIKE CONCAT('%', ifnull(:assetId,''), '%'))");
		}

		sb.append("    ORDER BY D1.ASSET_ID, D1.LOT_NO");

		Query query = entityManager.createNativeQuery(sb.toString());

		if (!StringUtils.isEmpty(siteId)) {// 사이트ID
			query.setParameter("siteId", siteId);
		}

		if (!StringUtils.isEmpty(assetId)) {// 설비ID
			query.setParameter("assetId", assetId);
		}

		return new JpaResultMapper().list(query, LearningLotNoDto.class);
	}

	@Override
	public ResponseEntity<String> saveLearningLotData(List<LearningLotNoDto>  dataList){
		if(dataList.size() > 0) {

			for(LearningLotNoDto lnLotDto : dataList) {

				if("Y".equals(lnLotDto.getLearningYn())){
					//학습데이터 여부 Y인 경우 Insert
					LearningLotNo c = new LearningLotNo(lnLotDto.getSiteId(),
							lnLotDto.getAssetId(),
							lnLotDto.getLotNo(),
							lnLotDto.getDataCnt(),
							LocalDateTime.now()
					);
					learningLotNoRepository.save(c);
				}
				else{
					//학습데이터 여부 Y가 아닌 경우 Delete
					LearningLotNoKey learningDataKey = new LearningLotNoKey(lnLotDto.getSiteId()
																		, lnLotDto.getAssetId()
																		, lnLotDto.getLotNo());

					learningLotNoRepository.deleteById(learningDataKey);
				}
			}
		}

		return new ResponseEntity<>("저장 되었습니다.", HttpStatus.OK);
	}

	/**
	 *
	 * @param siteId
	 * @param batchId
	 * @throws IOException
	 */
	@Override
	public void execLearningBatchPython(String siteId, String batchId) throws IOException {
		CommandLine commandLine = CommandLine.parse("python");

		if("pca".equals(batchId)){
			//commandLine.addArgument("C:\\workspace\\etri_python\\Batch_pca_chart.py");
			commandLine.addArgument("/home/webdev/apps/etri-rs/Batch_pca_chart.py");
		}
		else if("predict".equals(batchId)){
			//commandLine.addArgument("C:\\workspace\\etri_python\\Batch_predict_chart.py");
			commandLine.addArgument("/home/webdev/apps/etri-rs/Batch_predict_chart.py");
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
		DefaultExecutor executor = new DefaultExecutor();

		executor.setStreamHandler(pumpStreamHandler);

		int result = executor.execute(commandLine);

		System.out.println("result: " + result);
		System.out.println("output: " + outputStream.toString());
	}
}
