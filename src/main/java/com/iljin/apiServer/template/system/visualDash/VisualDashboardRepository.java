package com.iljin.apiServer.template.system.visualDash;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisualDashboardRepository extends JpaRepository<VisualDashboard, VisualDashboardKey> {
    Optional<VisualDashboard> findByLoginId(String loginId);
}
