package com.iljin.apiServer.template.bigdata.topic;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Transactional
public interface BigdataTopicService {
	List<BigdataTopicDto> getTopicList(BigdataTopicDto topicDto);

	@Modifying
	@Transactional
	ResponseEntity<String> saveTopic(List<BigdataTopicDto> connectList);

	@Modifying
	@Transactional
	ResponseEntity<String> deleteTopic(String siteId, String connectId, String topicId);

	/**
	 * 토픽 작동 (시작/중지)
	 * */
	@Modifying
	@Transactional
	ResponseEntity<String> doOperateTopic(@RequestBody BigdataTopicDto topicDto);

}
