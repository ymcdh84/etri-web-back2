
package com.iljin.apiServer.template.bigdata.tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BigdataAssetTagServiceImpl implements BigdataAssetTagService {

	@PersistenceContext
	EntityManager entityManager;

	private final AssetTagRepository assetTagRepository;

	@Override
	public List<BigdataAssetTagDto> getAssetTag(BigdataAssetTagDto bigdataConnectDto){
		String siteId = bigdataConnectDto.getSiteId();
		String assetId = bigdataConnectDto.getAssetId();
		String tagId = bigdataConnectDto.getTagId();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT D1.SITE_ID\n" +
				"\t , D1.ASSET_ID\n" +
				"\t , D1.TAG_ID\n" +
				"\t , D1.TAG_NM\n" +
				"\t , D1.TAG_DESC\n" +
				"\t , D1.DATA_TYPE_CD\n" +
				"\t , D1.CYCLE\n" +
				"\t , D1.IMPORTANCE_CD\n" +
				"\t , D1.ALIAS\n" +
				"\t , D1.INPUT_MIN_VAL\n" +
				"\t , D1.INPUT_MAX_VAL\n" +
				"\t , D1.FORMAT\n" +
				"\t , D1.ALARM_INFO_MIN_VAL\n" +
				"\t , D1.ALARM_INFO_MAX_VAL\n" +
				"\t , D1.ALARM_WORM_MIN_VAL\n" +
				"\t , D1.ALARM_WORM_MAX_VAL\n" +
				"\t , D1.ALARM_EMER_MIN_VAL\n" +
				"\t , D1.ALARM_EMER_MAX_VAL\n" +
				"\t , D1.USE_YN\n" +
				"\t , D1.DATA_CNT\n" +
				"\t , D1.MAPPING_KEY\n" +
				"\t , D2.ASSET_NM \n" +
				"\t , D2.MODEL_CONFIRM_YN \n" +
				"  FROM TB_BIGDATA_ASSET_TAG D1\n" +
				"       INNER JOIN TB_BIGDATA_ASSET D2\n" +
				"               ON D1.SITE_ID = D2.SITE_ID \n" +
				"              AND D1.ASSET_ID = D2.ASSET_ID\n" +
				" WHERE D1.SITE_ID = :siteId\n" +
				"   AND D1.ASSET_ID = :assetId\n"
		);

		if (!StringUtils.isEmpty(tagId)) {// 태그ID
			sb.append("   AND D1.TAG_ID = :tagId\n");
		}

		Query query = entityManager.createNativeQuery(sb.toString());

		query.setParameter("siteId", siteId);
		query.setParameter("assetId", assetId);

		if (!StringUtils.isEmpty(tagId)) {// 태그ID
			query.setParameter("tagId", tagId);
		}

		return new JpaResultMapper().list(query, BigdataAssetTagDto.class);
	}

	@Override
	public ResponseEntity<String> saveAssetTag(@RequestBody BigdataAssetTagDto bigdataAssetTagDto) {

		String siteId = bigdataAssetTagDto.getSiteId();
		String assetId = bigdataAssetTagDto.getAssetId();
		String tagId = bigdataAssetTagDto.getTagId();

		BigdataAssetTagKey bigdataAssetTagKey = new BigdataAssetTagKey(siteId, assetId, tagId);

		Optional<AssetTag> tagInfo = assetTagRepository.findById(bigdataAssetTagKey);
		if(tagInfo.isPresent()) {
			//update
			tagInfo.ifPresent(c -> {
				c.updateAssetTag(
						bigdataAssetTagDto.getTagNm(),
						bigdataAssetTagDto.getTagDesc(),
						bigdataAssetTagDto.getDataTypeCd(),
						bigdataAssetTagDto.getCycle(),
						bigdataAssetTagDto.getImportanceCd(),
						bigdataAssetTagDto.getAlias(),
						bigdataAssetTagDto.getInputMinVal(),
						bigdataAssetTagDto.getInputMaxVal(),
						bigdataAssetTagDto.getFormat(),
						bigdataAssetTagDto.getAlarmInfoMinVal(),
						bigdataAssetTagDto.getAlarmInfoMaxVal(),
						bigdataAssetTagDto.getAlarmWormMinVal(),
						bigdataAssetTagDto.getAlarmWormMaxVal(),
						bigdataAssetTagDto.getAlarmEmerMinVal(),
						bigdataAssetTagDto.getAlarmEmerMaxVal(),
						bigdataAssetTagDto.getUseYn(),
						bigdataAssetTagDto.getDataCnt(),
						bigdataAssetTagDto.getMappingKey()
				);

				assetTagRepository.save(c);
			});
		}

		return new ResponseEntity<>("저장 되었습니다.", HttpStatus.OK);
	}

	@Override
	public List<BigdataAssetTagDto> getTopicBlinkAliasOrDataType(String siteId, String assetId){

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT D1.SITE_ID\n" +
				"\t , D1.ASSET_ID\n" +
				"\t , D1.TAG_ID\n" +
				"\t , D1.TAG_NM\n" +
				"\t , D1.TAG_DESC\n" +
				"\t , D1.DATA_TYPE_CD\n" +
				"\t , D1.CYCLE\n" +
				"\t , D1.IMPORTANCE_CD\n" +
				"\t , D1.ALIAS\n" +
				"\t , D1.INPUT_MIN_VAL\n" +
				"\t , D1.INPUT_MAX_VAL\n" +
				"\t , D1.FORMAT\n" +
				"\t , D1.ALARM_INFO_MIN_VAL\n" +
				"\t , D1.ALARM_INFO_MAX_VAL\n" +
				"\t , D1.ALARM_WORM_MIN_VAL\n" +
				"\t , D1.ALARM_WORM_MAX_VAL\n" +
				"\t , D1.ALARM_EMER_MIN_VAL\n" +
				"\t , D1.ALARM_EMER_MAX_VAL\n" +
				"\t , D1.USE_YN\n" +
				"\t , D1.DATA_CNT\n" +
				"\t , D1.MAPPING_KEY\n" +
				"\t , D2.ASSET_NM \n" +
				"  FROM TB_BIGDATA_ASSET_TAG D1\n" +
				"       INNER JOIN TB_BIGDATA_ASSET D2\n" +
				"               ON D1.SITE_ID = D2.SITE_ID \n" +
				"              AND D1.ASSET_ID = D2.ASSET_ID\n" +
				" WHERE D1.SITE_ID = :siteId\n" +
				"   AND D1.ASSET_ID = :assetId\n" +
				"   AND (D1.ALIAS IS NULL OR D1.ALIAS = '' OR D1.DATA_TYPE_CD IS NULL OR D1.DATA_TYPE_CD = '')\n" +
				"  LIMIT 1 ");

		Query query = entityManager.createNativeQuery(sb.toString());

		query.setParameter("siteId", siteId);
		query.setParameter("assetId", assetId);

        return new JpaResultMapper().list(query, BigdataAssetTagDto.class);
	}

	@Override
	public String getNewTopicId(String siteId, String assetId){

		StringBuilder sb = new StringBuilder();
		sb.append("\tSELECT CONCAT(:assetId,'-',LPAD(IFNULL(MAX(SUBSTRING(TAG_ID, LENGTH(ASSET_ID) + 2)),0) + 1, 3,'0')) as TAG_ID\n" +
				"\t  FROM TB_BIGDATA_ASSET_TAG\n" +
				"\t WHERE SITE_ID = :siteId\n" +
				"\t   AND ASSET_ID = :assetId");

		Query query = entityManager.createNativeQuery(sb.toString());

		query.setParameter("siteId", siteId);
		query.setParameter("assetId", assetId);

		return query.getSingleResult().toString();
	}
}