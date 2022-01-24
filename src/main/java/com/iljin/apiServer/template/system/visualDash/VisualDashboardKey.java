package com.iljin.apiServer.template.system.visualDash;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class VisualDashboardKey implements Serializable {
    String loginId;
    String dashboardId;

    @Builder
    public VisualDashboardKey(String loginId, String dashboardId) {
        this.loginId = loginId;
        this.dashboardId = dashboardId;
    }
}
