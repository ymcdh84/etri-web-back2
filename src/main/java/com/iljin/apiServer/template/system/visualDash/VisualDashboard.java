package com.iljin.apiServer.template.system.visualDash;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_visual_dashboard")
@IdClass(VisualDashboardKey.class)
public class VisualDashboard {
    @Id
    @Column(name = "login_id")
    String loginId;

    @Id
    @Column(name = "dashboard_id")
    String dashboardId;

    @Column(name = "modified_date")
    LocalDateTime modifiedDate;

    @Builder
    public VisualDashboard (String loginId, String dashboardId, LocalDateTime modifiedDate) {
        this.loginId = loginId;
        this.dashboardId = dashboardId;
        this.modifiedDate = modifiedDate;
    }
}
