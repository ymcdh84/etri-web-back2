package com.iljin.apiServer.template.bigdata.tag;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Transactional
public interface BigdataAssetTagService {
	List<BigdataAssetTagDto> getAssetTag(BigdataAssetTagDto connectDto);

	@Modifying
	@Transactional
	ResponseEntity<String> saveAssetTag(@RequestBody BigdataAssetTagDto bigdataAssetTagDto);

	List<BigdataAssetTagDto> getTopicBlinkAliasOrDataType(String siteId, String assetId);

	String getNewTopicId(String siteId, String assetId);
}
