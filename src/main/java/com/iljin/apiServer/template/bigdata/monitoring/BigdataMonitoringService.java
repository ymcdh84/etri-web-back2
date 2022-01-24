package com.iljin.apiServer.template.bigdata.monitoring;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BigdataMonitoringService {
	List<List<Double>> getContTestList(BigdataMonitoringDto moDto);

	List<List<Double>> getContMeanList(BigdataMonitoringDto moDto);
}
