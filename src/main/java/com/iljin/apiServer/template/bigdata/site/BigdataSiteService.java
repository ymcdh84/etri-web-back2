package com.iljin.apiServer.template.bigdata.site;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BigdataSiteService {
	List<BigdataSiteDto> getSiteList(BigdataSiteDto siteDto);

	@Modifying
	@Transactional
	ResponseEntity<String> saveSite(List<BigdataSiteDto> siteList);

	@Modifying
	@Transactional
	ResponseEntity<String> deleteSite(String siteId);
}
