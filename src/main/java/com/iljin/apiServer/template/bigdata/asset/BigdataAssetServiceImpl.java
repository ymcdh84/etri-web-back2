package com.iljin.apiServer.template.bigdata.asset;

import com.iljin.apiServer.core.files.FileDto;
import com.iljin.apiServer.core.files.FileService;
import com.iljin.apiServer.template.bigdata.tag.AssetTag;
import com.iljin.apiServer.template.bigdata.tag.AssetTagRepository;
import com.iljin.apiServer.template.bigdata.tag.BigdataAssetTagDto;
import com.iljin.apiServer.template.bigdata.tag.BigdataAssetTagService;
import com.iljin.apiServer.template.system.code.CodeDto;
import com.iljin.apiServer.template.system.code.CodeService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BigdataAssetServiceImpl implements BigdataAssetService {

	private final FileService ufileService;

	@PersistenceContext
	EntityManager entityManager;

	private final AssetAlarmRepository assetAlarmRepository;

	private final BigdataAssetRepository bigdataAssetRepository;

	private final AssetTagRepository assetTagRepository;

	private final BigdataAssetTagService bigdataAssetTagService;

	private final CodeService codeService;
	@Override
	public List<BigdataAssetDto> getAsset(BigdataAssetDto bigdataAssetDto) {
		String siteId = bigdataAssetDto.getSiteId();
		String assetId = bigdataAssetDto.getAssetId();

		BigdataAssetKey bigdataAssetKey = new BigdataAssetKey(siteId, assetId);

		List<BigdataAssetDto> assetList = new ArrayList<>();

		//1. get Group Code List by Search conditions (
		Optional<BigdataAsset> optionalAsset = bigdataAssetRepository.findById(bigdataAssetKey);

		if(optionalAsset.isPresent()) {
			BigdataAsset bigdataAsset = optionalAsset.get();

			assetList.add(new BigdataAssetDto(bigdataAsset.getSiteId()
					, bigdataAsset.getAssetId()
					, bigdataAsset.getAssetNm()
					, (Integer)bigdataAsset.getAssetGroup()
					, bigdataAsset.getAssetDesc()
					, bigdataAsset.getUpAssetId()
					, bigdataAsset.getAssetStatCd()
			));
		}
		return assetList;
	}

	@Override
	public List<BigdataAssetDto> getAssetWithImage(BigdataAssetDto bigdataAssetDto) {
		String siteId = bigdataAssetDto.getSiteId();
		String assetId = bigdataAssetDto.getAssetId();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT D1.SITE_ID\n" +
				"     , D1.ASSET_ID\n" +
				"     , D1.ASSET_NM\n" +
				"     , D1.ASSET_GROUP \n" +
				"     , D1.ASSET_DESC \n" +
				"     , D1.UP_ASSET_ID \n" +
				"     , D1.ASSET_STAT_CD \n" +
				"     , D1.MODEL_CONFIRM_YN \n" +
				"     , D2.ORIGINAL_NAME AS FILE_NAME \n" +
				"     , D2.STORED_NAME \n" +
				"     , D2.DOWNLOAD_URL \n" +
				"  FROM TB_BIGDATA_ASSET D1\n" +
				"       LEFT OUTER JOIN A_FILE D2 \n" +
				"               ON D1.ASSET_ID = D2.DOCUMENT_H_ID\n" +
				" WHERE 1=1 ");

		if (!StringUtils.isEmpty(siteId)) {// ?????????ID
			sb.append("   AND D1.SITE_ID = :siteId");
		}
		if (!StringUtils.isEmpty(assetId)) {// ??????ID
			sb.append("   AND D1.ASSET_ID = :assetId");
		}

		Query query = entityManager.createNativeQuery(sb.toString());

		if (!StringUtils.isEmpty(siteId)) {// ?????????ID
			query.setParameter("siteId", siteId);
		}
		if (!StringUtils.isEmpty(assetId)) {// ??????ID
			query.setParameter("assetId", assetId);
		}


		return new JpaResultMapper().list(query, BigdataAssetDto.class);
	}

	@Override
	public List<BigdataAssetDto> getAssetTagList(BigdataAssetDto bigdataAssetDto) {
		String siteId = bigdataAssetDto.getSiteId();
		String assetId = bigdataAssetDto.getAssetId();
		String tagId = bigdataAssetDto.getTagId();
		String dataTypeCd = bigdataAssetDto.getDataTypeCd();

		List<BigdataAssetDto> connectList = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT D3.SITE_ID\n" +
				"     , D3.SITE_NM\n" +
				"     , D1.ASSET_ID\n" +
				"     , D1.ASSET_NM \n" +
				"     , D2.TAG_ID \n" +
				"     , D2.TAG_NM \n" +
				"     , D2.DATA_TYPE_CD \n" +
				"     , D2.DATA_CNT \n" +
				"     , D1.ASSET_DESC \n" +
				"  FROM TB_BIGDATA_ASSET D1\n" +
				"       INNER JOIN TB_BIGDATA_ASSET_TAG D2 \n" +
				"               ON D1.SITE_ID = D2.SITE_ID  \n" +
				"              AND D1.ASSET_ID = D2.ASSET_ID\n" +
				"       INNER JOIN TB_BIGDATA_SITE D3 \n" +
				"               ON D3.SITE_ID = D1.SITE_ID\n" +
				" WHERE 1=1 ");

		if (!StringUtils.isEmpty(siteId)) {// ?????????ID
			sb.append("   AND (D3.SITE_ID LIKE CONCAT('%', ifnull(:siteId,''), '%')" +
					  "        OR D3.SITE_NM LIKE CONCAT('%', ifnull(:siteId,''), '%'))");
		}
		if (!StringUtils.isEmpty(assetId)) {// ??????ID
			sb.append("   AND (D1.ASSET_ID LIKE CONCAT('%', ifnull(:assetId,''), '%')" +
					  "        OR D1.ASSET_NM LIKE CONCAT('%', ifnull(:assetId,''), '%'))");
		}
		if (!StringUtils.isEmpty(tagId)) {// ??????ID
			sb.append("   AND (D2.TAG_ID LIKE CONCAT('%', ifnull(:tagId,''), '%')" +
					"        OR D2.TAG_NM LIKE CONCAT('%', ifnull(:tagId,''), '%'))");
		}
		if (!StringUtils.isEmpty(dataTypeCd)) {// ???????????????
			sb.append("   AND (D2.DATA_TYPE_CD LIKE CONCAT('%', ifnull(:dataTypeCd,''), '%'))");
		}
//		if (!StringUtils.isEmpty(dataTypeCd)) {// ???????????????
//			sb.append(" AND D2.DATA_TYPE_CD = :dataTypeCd");
//		}
		sb.append(" ORDER BY D3.SITE_ID, D1.ASSET_ID, D2.TAG_ID");

		Query query = entityManager.createNativeQuery(sb.toString());

		if (!StringUtils.isEmpty(siteId)) {// ?????????ID
			query.setParameter("siteId", siteId);
		}
		if (!StringUtils.isEmpty(assetId)) {// ??????ID
			query.setParameter("assetId", assetId);
		}
		if (!StringUtils.isEmpty(tagId)) {// ??????ID
			query.setParameter("tagId", tagId);
		}		
		if (!StringUtils.isEmpty(dataTypeCd)) {// ???????????????
			query.setParameter("dataTypeCd", dataTypeCd);
		}

		return new JpaResultMapper().list(query, BigdataAssetDto.class);
	}

	@Override
	public List<BigdataAssetDto> getAlarmList(BigdataAssetDto bigdataAssetDto) {
		String siteId = bigdataAssetDto.getSiteId();
		String assetId = bigdataAssetDto.getAssetId();
		String tagId = bigdataAssetDto.getTagId();
		String dataTypeCd = bigdataAssetDto.getDataTypeCd();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT D3.SITE_ID\n" +
				"     , D3.SITE_NM\n" +
				"     , D1.ASSET_ID\n" +
				"     , D1.ASSET_NM \n" +
				"     , D2.TAG_ID \n" +
				"     , D2.TAG_NM \n" +
				"     , D4.ALARM_ID \n" +
				"     , DATE_FORMAT(D4.ALARM_DATE_TIME, '%Y-%m-%d %T') AS ALARM_DATE_TIME \n" +
				"     , CAST(D4.ALARM_VAL as DOUBLE) AS ALARM_VAL \n" +
				"     , D4.ALARM_LEVEL_CD \n" +
				"     , D4.ALARM_DESC \n" +
				"     , D4.IMPORTANCE_CD \n" +
				"     , CAST(D4.CONFIRM_YN AS CHAR) AS CONFIRM_YN \n" +
				"  FROM TB_BIGDATA_ASSET D1\n" +
				"       INNER JOIN TB_BIGDATA_ASSET_TAG D2 \n" +
				"               ON D1.SITE_ID = D2.SITE_ID  \n" +
				"              AND D1.ASSET_ID = D2.ASSET_ID\n" +
				"       INNER JOIN TB_BIGDATA_SITE D3 \n" +
				"               ON D3.SITE_ID = D1.SITE_ID\n" +
				"       INNER JOIN TB_BIGDATA_ALARM_HISTORY D4\n" +
				"               ON D2.SITE_ID = D4.SITE_ID\n" +
				"              AND D2.ASSET_ID = D4.ASSET_ID\n" +
				"              AND D2.TAG_ID = D4.TAG_ID\n" +
				" WHERE 1=1 ");

		if (!StringUtils.isEmpty(siteId)) {// ?????????ID
			sb.append("   AND (D3.SITE_ID LIKE CONCAT('%', ifnull(:siteId,''), '%')" +
					"        OR D3.SITE_NM LIKE CONCAT('%', ifnull(:siteId,''), '%'))");
		}
		if (!StringUtils.isEmpty(assetId)) {// ??????ID
			sb.append("   AND (D1.ASSET_ID LIKE CONCAT('%', ifnull(:assetId,''), '%')" +
					"        OR D1.ASSET_NM LIKE CONCAT('%', ifnull(:assetId,''), '%'))");
		}
		if (!StringUtils.isEmpty(tagId)) {// ??????ID
			sb.append("   AND (D2.TAG_ID LIKE CONCAT('%', ifnull(:tagId,''), '%')" +
					"        OR D2.TAG_NM LIKE CONCAT('%', ifnull(:tagId,''), '%'))");
		}
		if (!StringUtils.isEmpty(dataTypeCd)) {// ???????????????
			sb.append("   AND (D2.DATA_TYPE_CD LIKE CONCAT('%', ifnull(:dataTypeCd,''), '%'))");
		}
//		if (!StringUtils.isEmpty(dataTypeCd)) {// ???????????????
//			sb.append(" AND D2.DATA_TYPE_CD = :dataTypeCd");
//		}
		sb.append(" ORDER BY D3.SITE_ID, D1.ASSET_ID, D2.TAG_ID, D4.ALARM_ID");

		Query query = entityManager.createNativeQuery(sb.toString());

		if (!StringUtils.isEmpty(siteId)) {// ?????????ID
			query.setParameter("siteId", siteId);
		}
		if (!StringUtils.isEmpty(assetId)) {// ??????ID
			query.setParameter("assetId", assetId);
		}
		if (!StringUtils.isEmpty(tagId)) {// ??????ID
			query.setParameter("tagId", tagId);
		}
		if (!StringUtils.isEmpty(dataTypeCd)) {// ???????????????
			query.setParameter("dataTypeCd", dataTypeCd);
		}

		return new JpaResultMapper().list(query, BigdataAssetDto.class);
	}

	@Override
	public List<BigdataAssetDto> getAlarmMonitoringList(BigdataAssetDto bigdataAssetDto) {
		String siteId = bigdataAssetDto.getSiteId();
		String assetId = bigdataAssetDto.getAssetId();
		String tagId = bigdataAssetDto.getTagId();
		String dataTypeCd = bigdataAssetDto.getDataTypeCd();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT D1.SITE_ID\n" +
				  "     , D1.ASSET_ID\n" +
				  "     , D1.ALARM_ID \n" +
				  "     , DATE_FORMAT(D1.ALARM_DATE_TIME, '%Y-%m-%d %T') AS ALARM_DATE_TIME \n" +
				  "     , ROUND(CAST(D1.ALARM_VAL as DOUBLE),2) AS ALARM_VAL \n" +
				  "     , D1.ALARM_LEVEL_CD \n" +
				  "     , FN_CMN_GET_CODE_NM('ALARM_LEVEL_CD',D1.ALARM_LEVEL_CD) as ALARM_LEVEL_NM \n" +
				  "     , D1.ALARM_DESC \n" +
				  "  FROM TB_BIGDATA_ALARM_HISTORY D1 \n" +
				  " WHERE 1=1   ");

		if (!StringUtils.isEmpty(siteId)) {// ?????????ID
			sb.append("   AND (D1.SITE_ID LIKE CONCAT('%', ifnull(:siteId,''), '%'))");
		}
		if (!StringUtils.isEmpty(assetId)) {// ??????ID
			sb.append("   AND (D1.ASSET_ID LIKE CONCAT('%', ifnull(:assetId,''), '%'))");
		}
		sb.append(" ORDER BY D1.SITE_ID, D1.ASSET_ID, D1.ALARM_ID DESC");
		sb.append(" limit 5");

		Query query = entityManager.createNativeQuery(sb.toString());

		if (!StringUtils.isEmpty(siteId)) {// ?????????ID
			query.setParameter("siteId", siteId);
		}
		if (!StringUtils.isEmpty(assetId)) {// ??????ID
			query.setParameter("assetId", assetId);
		}

		return new JpaResultMapper().list(query, BigdataAssetDto.class);
	}

	@Override
	public ResponseEntity<String> saveAlarmConfirm(List<BigdataAssetDto> dataList) {
		if(dataList.size() > 0) {
			for(BigdataAssetDto bigdataAssetDto : dataList) {
				String alarmId = bigdataAssetDto.getAlarmId();

				Optional<AssetAlarm> alarm = assetAlarmRepository.findById(alarmId);
				if(alarm.isPresent()) {
					//update
					alarm.ifPresent(c -> {
						c.updateAssetAlarmConfirm("Y");
						assetAlarmRepository.save(c);
					});
				}
			}
		}

		return new ResponseEntity<>("?????? ???????????????.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteAlarm(String alarmId){

		assetAlarmRepository.deleteById(alarmId);

		return new ResponseEntity<>("?????????????????????.", HttpStatus.OK);
	}

	@Override
	public List<BigdataAssetDto> getAssetTreeList(BigdataAssetDto bigdataAssetDto) {
		String siteId = bigdataAssetDto.getSiteId();
		String assetId = bigdataAssetDto.getAssetId();
		String modelConfirmYn = bigdataAssetDto.getModelConfirmYn();

		List<BigdataAssetDto> connectList = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		sb.append("WITH RECURSIVE CTE AS\n" +
				"(\n" +
				"SELECT SITE_ID \n" +
				"     , ASSET_ID \n" +
				"     , ASSET_NM\n" +
				"     , ASSET_GROUP\n" +
				"     , ASSET_DESC\n" +
				"     , UP_ASSET_ID     \n" +
				"     , ASSET_STAT_CD\n" +
				"     , 1 AS LV\n" +
				"  FROM TB_BIGDATA_ASSET\n" +
				" WHERE ASSET_GROUP = 1\n");

		if (!StringUtils.isEmpty(siteId)) {// ?????????ID
			sb.append("   AND SITE_ID = :siteId");
		}

		if (!StringUtils.isEmpty(assetId)) {// ??????ID
			sb.append("   AND ASSET_ID = :assetId");
		}

		if (!StringUtils.isEmpty(modelConfirmYn)) {// ??????????????????
			sb.append("   AND MODEL_CONFIRM_YN = :modelConfirmYn");
		}

		sb.append(" UNION ALL\n" +
				"SELECT C.SITE_ID \n" +
				"     , C.ASSET_ID \n" +
				"     , C.ASSET_NM\n" +
				"     , C.ASSET_GROUP\n" +
				"     , C.ASSET_DESC\n" +
				"     , C.UP_ASSET_ID     \n" +
				"     , C.ASSET_STAT_CD\n" +
				"     , P.LV + 1 AS LV\n" +
				"  FROM TB_BIGDATA_ASSET C\n" +
				" INNER JOIN CTE P\n" +
				"    ON C.UP_ASSET_ID = P.ASSET_ID\n" +
				")\n" +
				"SELECT D1.SITE_ID, D1.ASSET_ID, D1.ASSET_NM, D1.ASSET_GROUP, D1.ASSET_DESC, D1.UP_ASSET_ID, D1.ASSET_STAT_CD, D1.LV\n" +
				"  FROM CTE D1\n" +
				" WHERE 1=1 ");

		if (!StringUtils.isEmpty(assetId)) {// ??????ID
			sb.append(" ORDER BY D1.LV DESC");
		}else{
			sb.append(" ORDER BY D1.ASSET_ID, D1.LV DESC");
		}


		Query query = entityManager.createNativeQuery(sb.toString());

		if (!StringUtils.isEmpty(siteId)) {// ?????????ID
			query.setParameter("siteId", siteId);
		}

		if (!StringUtils.isEmpty(assetId)) {// ??????ID
			query.setParameter("assetId", assetId);
		}

		if (!StringUtils.isEmpty(modelConfirmYn)) {// ?????? ?????? ??????
			query.setParameter("modelConfirmYn", modelConfirmYn);
		}		

		return new JpaResultMapper().list(query, BigdataAssetDto.class);
	}

	@Override
	public ResponseEntity<String> saveAssetNew(@RequestBody BigdataAssetDto bigdataAssetDto) {

		String siteId = bigdataAssetDto.getSiteId();
		String assetId = bigdataAssetDto.getAssetId();

		BigdataAssetKey bigdataAssetTKey = new BigdataAssetKey(siteId, assetId);

		Optional<BigdataAsset> bigdataAsset = bigdataAssetRepository.findById(bigdataAssetTKey);

		if(bigdataAsset.isPresent()) {
			//update
			throw new RuntimeException("????????? ID??? ???????????? ?????? ????????? ???????????????. ID ????????? ????????? ????????????.");
		} else {
			//insert
			BigdataAsset c = new BigdataAsset(bigdataAssetDto.getSiteId(),
					bigdataAssetDto.getAssetId(),
					bigdataAssetDto.getAssetNm(),
					bigdataAssetDto.getAssetGroup(),
					bigdataAssetDto.getUpAssetId(),
					bigdataAssetDto.getAssetDesc(),
					bigdataAssetDto.getAssetStatCd(),
					bigdataAssetDto.getModelConfirmYn()
			);

			bigdataAssetRepository.save(c);
		}

		return new ResponseEntity<>("?????? ???????????????.", HttpStatus.OK);
	}
	@Override
	public ResponseEntity<String> saveAsset(@RequestBody BigdataAssetDto bigdataAssetDto) {

		String siteId = bigdataAssetDto.getSiteId();
		String assetId = bigdataAssetDto.getAssetId();


		BigdataAssetKey bigdataAssetTKey = new BigdataAssetKey(siteId, assetId);

		Optional<BigdataAsset> bigdataAsset = bigdataAssetRepository.findById(bigdataAssetTKey);
		if(bigdataAsset.isPresent()) {
			//update
			bigdataAsset.ifPresent(c -> {
				c.updateAsset(
						bigdataAssetDto.getAssetNm(),
						(Integer)bigdataAssetDto.getAssetGroup(),
						bigdataAssetDto.getUpAssetId(),
						bigdataAssetDto.getAssetDesc(),
						bigdataAssetDto.getAssetStatCd(),
						bigdataAssetDto.getModelConfirmYn()
				);

				bigdataAssetRepository.save(c);
			});
		}
		
		return new ResponseEntity<>("?????? ???????????????.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteAsset(String siteId, String assetId){

		//[STEP-1] ?????? ????????? ?????? ?????? ??????
		BigdataAssetKey bigdataAssetKey = new BigdataAssetKey(siteId, assetId);

		Optional<BigdataAsset> assetInfo = bigdataAssetRepository.findById(bigdataAssetKey);

		if(assetInfo.isPresent()) {
			if("Y".equals(assetInfo.get().getModelConfirmYn())){
				//error
				throw new RuntimeException("????????? ????????? ????????? ????????? ??? ????????????.");
			}
		}

		//?????? ??????
		assetTagRepository.deleteBySiteIdAndAssetId(siteId, assetId);

		//?????? ??????
		BigdataAssetKey bigdataAssettKey = new BigdataAssetKey(siteId, assetId);

		bigdataAssetRepository.deleteById(bigdataAssettKey);

		//????????? ?????? ??????
		FileDto fileDto = new FileDto();

		fileDto.setDocumentHId(assetId);

		ufileService.deleteAssetFiles(fileDto);

		return new ResponseEntity<>("?????????????????????.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> saveRegAssetTag(@RequestBody BigdataAssetDto bigdataAssetDto) {

		String siteId = bigdataAssetDto.getSiteId();
		String assetId = bigdataAssetDto.getAssetId();
		String mappingKey =  bigdataAssetDto.getMappingKey();
		String tagNm = bigdataAssetDto.getTagNm();
		String dataTypeCd = bigdataAssetDto.getDataTypeCd();

		//[STEP-0] ?????? ????????? ?????? ?????? ??? ????????????
		CodeDto codeDto = new CodeDto();
		codeDto.setCompCd("101600");
		codeDto.setGroupCd("DATA_TYPE_CD");

		List<CodeDto> list = codeService.getGroupCodeDetailList(codeDto);

		//[STEP-1] ?????? ????????? ?????? ?????? ??????
		BigdataAssetKey bigdataAssetKey = new BigdataAssetKey(siteId, assetId);

		Optional<BigdataAsset> assetInfo = bigdataAssetRepository.findById(bigdataAssetKey);

		if(assetInfo.isPresent()) {
			if("Y".equals(assetInfo.get().getModelConfirmYn())){
				//error
				throw new RuntimeException("?????? ????????? ???????????? ?????? ????????? ??? ????????????.");
			}
		}

		//[STEP-2] ?????? ???????????? ?????? ???????????? ??????
		List<AssetTag> tagInfo = assetTagRepository.findBySiteIdAndAssetIdAndMappingKey(siteId, assetId, mappingKey);

		if(tagInfo.size() > 0) {
			//error
			throw new RuntimeException("?????? ????????? ?????? ?????????.");
		} else {
			//insert
			AssetTag tag = new AssetTag();

			tag.setSiteId(siteId);
			tag.setAssetId(assetId);
			tag.setTagNm(tagNm);
			tag.setAlias(mappingKey);
			tag.setMappingKey(mappingKey);

			//??????ID ??????
			String tagId = bigdataAssetTagService.getNewTopicId(siteId, assetId);
			tag.setTagId(tagId);

			//????????? ?????? ??????
			list.forEach(c-> {
				if(c.getRemark1().contains(dataTypeCd)){
					tag.setDataTypeCd(c.getDetailCd());
				}
			});

			assetTagRepository.save(tag);
		}

		return new ResponseEntity<>("?????? ???????????????.", HttpStatus.OK);
	}

	@Override
	public void callSpAssetModeling(String siteId, String assetId) {

		//[STEP-1] ????????? ???????????? ??????
		BigdataAssetKey bigdataAssetKey = new BigdataAssetKey(siteId, assetId);

		Optional<BigdataAsset> assetInfo = bigdataAssetRepository.findById(bigdataAssetKey);

		if(assetInfo.isPresent()){
			if("Y".equals(assetInfo.get().getModelConfirmYn())){
				throw new RuntimeException(assetInfo.get().getAssetNm() + "???(???) ?????? ????????? ????????? ???????????????.");
			}
		}

		//[STEP-2] ????????? ?????? ??????[ALIAS, DATA_TYPE_CD] ?????? ??????
		BigdataAssetDto assetDto =  new BigdataAssetDto();

		assetDto.setSiteId(siteId);
		assetDto.setAssetId(assetId);

		List<BigdataAssetDto> assetlist = this.getAssetTreeList(assetDto);

		for(BigdataAssetDto asset : assetlist) {
			String siteIdT = asset.getSiteId();
			String assetT = asset.getAssetId();

			List<BigdataAssetTagDto> tagList = bigdataAssetTagService.getTopicBlinkAliasOrDataType(siteIdT, assetT);

			if(tagList.size() > 0){

				String assetNm = tagList.get(0).getAssetNm();
				String tagNm = tagList.get(0).getTagNm();
				String tagId = tagList.get(0).getTagId();

				String errMsg = assetNm + "[" + assetId + "]??? " + tagNm+ "[" + tagId + "] " + "??? ALIAS ?????? ??????????????? ????????? ???????????????. \n"
						        + "????????? ????????? ?????? ????????????.";

				throw new RuntimeException(errMsg);
			}
		}

		//[STEP-3] ????????? ???????????? ??????
		StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("SP_BIGDATA_ASSET_MODELING");
		storedProcedureQuery.setParameter("pSITE_ID", siteId);
		storedProcedureQuery.setParameter("pASSET_ID", assetId);

		storedProcedureQuery.execute();

		String oRtnCd = (String) storedProcedureQuery.getOutputParameterValue("oRtnCd");
		String oRtnMsg = (String) storedProcedureQuery.getOutputParameterValue("oRtnMsg");

		if (oRtnCd.equals("-1")) {
			throw new RuntimeException(oRtnMsg);
		}
	}

	@Override
	public void getAssetTagMappingStatus(BigdataAssetDto bigdataAssetDto) {
		String siteId = bigdataAssetDto.getSiteId();
		String assetId = bigdataAssetDto.getAssetId();
		String mappingKey =  bigdataAssetDto.getMappingKey();

		//[STEP-1] ?????? ????????? ?????? ?????? ??????
		BigdataAssetKey bigdataAssetKey = new BigdataAssetKey(siteId, assetId);

		Optional<BigdataAsset> assetInfo = bigdataAssetRepository.findById(bigdataAssetKey);

		if(assetInfo.isPresent()) {
			if("Y".equals(assetInfo.get().getModelConfirmYn())){
				//error
				throw new RuntimeException("?????? ????????? ???????????? ?????? ????????? ??? ????????????.");
			}
		}

		//[STEP-2] ?????? ???????????? ?????? ???????????? ??????
		List<AssetTag> tagInfo = assetTagRepository.findBySiteIdAndAssetIdAndMappingKey(siteId, assetId, mappingKey);

		if(tagInfo.size() > 0) {
			//error
			throw new RuntimeException("?????? ????????? ?????? ?????????.");
		}
	}

	@Override
	public ResponseEntity<String> saveCopyAssetTag(String siteId, String assetId, String copyAssetId){

		//[STEP-1] ?????? ?????? ?????? ?????? ?????? ??????
		assetTagRepository.deleteBySiteIdAndAssetId(siteId, assetId);

		//[STEP-2] ????????? ?????? ?????? ??????
		List<AssetTag> tagInfo = assetTagRepository.findBySiteIdAndAssetId(siteId, copyAssetId);

		//[STEP-3] ??????
		tagInfo.forEach(c -> {

			AssetTag tag = new AssetTag();
			tag.setSiteId(siteId);
			tag.setAssetId(assetId);
			tag.setTagId(assetId + "-" + c.getTagId().split("-")[1]);
			tag.setTagNm(c.getTagNm());
			tag.setTagDesc(c.getTagDesc());
			tag.setDataTypeCd(c.getDataTypeCd());
			tag.setAlias(c.getAlias());
			tag.setMappingKey(c.getMappingKey());

			assetTagRepository.save(tag);
		});

		return new ResponseEntity<>("?????? ???????????????.", HttpStatus.OK);
	}
}
