package com.iljin.apiServer.template.system.visualDash;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VisualDashboardService {
    @Modifying
    String setDashboardByLoginId(String dashboardId);

    String getDashboardByLoginId();
}
