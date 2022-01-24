package com.iljin.apiServer.template.bigdata.connect;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BigdataConnectService {
	List<BigdataConnectDto> getConnectList(BigdataConnectDto connectDto);

	@Modifying
	@Transactional
	ResponseEntity<String> saveConnect(List<BigdataConnectDto> connectList);

	@Modifying
	@Transactional
	ResponseEntity<String> deleteConnect(String siteId, String connectId);

	List<BigdataConnectDto> getSiteConnectList(BigdataConnectDto connectDto);
}
