package com.iljin.apiServer.template.bigdata.lndata;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Transactional
public interface LearningDataService {
	List<LearningLotNoDto> getLearningLotInfoList(LearningLotNoDto learningDto);

	List<LearningLotNoDto> getLearningLotData(LearningLotNoDto learningDto);

	@Modifying
	@Transactional
	ResponseEntity<String> saveLearningLotData(List<LearningLotNoDto>  dataList);

	void execLearningBatchPython(String siteId, String batchId) throws IOException;
}
