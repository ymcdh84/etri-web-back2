package com.iljin.apiServer.template.system.settings;

import com.iljin.apiServer.template.system.menu.Menu;
import com.iljin.apiServer.template.system.menu.MenuKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingsRepository extends JpaRepository<Menu, MenuKey> {
}
