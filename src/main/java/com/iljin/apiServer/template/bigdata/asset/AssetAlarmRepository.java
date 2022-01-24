package com.iljin.apiServer.template.bigdata.asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetAlarmRepository extends JpaRepository<AssetAlarm, String> {}
