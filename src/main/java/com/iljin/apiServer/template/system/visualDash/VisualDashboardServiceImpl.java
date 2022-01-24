package com.iljin.apiServer.template.system.visualDash;

import com.iljin.apiServer.core.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisualDashboardServiceImpl implements VisualDashboardService {
    private final Util util;
    private final VisualDashboardRepository visualDashboardRepository;

    @Override
    @Modifying
    public String setDashboardByLoginId(String dashboardId) {
        String loginId = util.getLoginId();

        /*
         * Step1. check before data
         * */
        visualDashboardRepository.findByLoginId(loginId).ifPresent(c -> {
            VisualDashboardKey visualDashboardKey = new VisualDashboardKey(c.getLoginId(), c.getDashboardId());
            visualDashboardRepository.deleteById(visualDashboardKey);
        });

        /*
         * Step2. add selected dashboardId
         * */
        VisualDashboard newVisualDashboard = new VisualDashboard(loginId, dashboardId, LocalDateTime.now());
        visualDashboardRepository.save(newVisualDashboard);

        return "success";
    }

    @Override
    public String getDashboardByLoginId() {
        String loginId = util.getLoginId();

        return visualDashboardRepository.findByLoginId(loginId).get().getDashboardId();
    }
}
