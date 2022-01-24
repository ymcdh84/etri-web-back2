package com.iljin.apiServer.template.bigdata.asset;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Transactional
public interface BigdataAssetService {
	List<BigdataAssetDto> getAssetTagList(BigdataAssetDto connectDto);

	List<BigdataAssetDto> getAlarmList(BigdataAssetDto connectDto);

	List<BigdataAssetDto> getAlarmMonitoringList(BigdataAssetDto connectDto);

	@Modifying
	@Transactional
	ResponseEntity<String> saveAlarmConfirm(List<BigdataAssetDto> dataList);

	@Modifying
	@Transactional
	ResponseEntity<String> deleteAlarm(String alarmId);

	List<BigdataAssetDto> getAssetTreeList(BigdataAssetDto connectDto);

	List<BigdataAssetDto> getAsset(BigdataAssetDto bigdataAssetDto);

	List<BigdataAssetDto> getAssetWithImage(BigdataAssetDto bigdataAssetDto);

	@Modifying
	@Transactional
	ResponseEntity<String> saveAssetNew(@RequestBody BigdataAssetDto bigdataAssetDto);

	@Modifying
	@Transactional
	ResponseEntity<String> saveAsset(@RequestBody BigdataAssetDto bigdataAssetDto);

	@Modifying
	@Transactional
	ResponseEntity<String> deleteAsset(String siteId, String assetId);

	@Modifying
	@Transactional
	ResponseEntity<String> saveRegAssetTag(@RequestBody BigdataAssetDto bigdataAssetDto);

	void callSpAssetModeling(String siteId, String assetId);

	void getAssetTagMappingStatus(BigdataAssetDto bigdataAssetDto);

	@Modifying
	@Transactional
	ResponseEntity<String> saveCopyAssetTag(String siteId, String assetId, String copyAssetId);
}
