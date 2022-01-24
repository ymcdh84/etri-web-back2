package com.iljin.apiServer.template.system.authority;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuthorityService {
    List<AuthorityDto> getAuthority(String compCd);

    @Modifying
    @Transactional
    ResponseEntity<String> saveAuthorities(List<AuthorityDto> authList);

    @Modifying
    @Transactional
    ResponseEntity<String> deleteAuthority(String roleCd, String compCd);

    List<MenuAuthDto> getMenuByAuthority(String roleCd, String compCd);

    @Modifying
    @Transactional
    ResponseEntity<String> saveMenuByAuthority(String roleCd, String compCd, List<MenuAuthDto> menuAuthList);

    List<AuthorityDto> getUserInfoByAuthority(String roleCd, String compCd);

    @Modifying
    @Transactional
    ResponseEntity<String> saveUserInfoByAuthority(String roleCd, String compCd, List<AuthorityDto> authList);
}
