package com.iljin.apiServer.template.system.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuAuthRepository extends JpaRepository<MenuAuth, MenuAuthKey> {
    void deleteByRoleCdAndCompCd(String roleCd, String compCd);
}
