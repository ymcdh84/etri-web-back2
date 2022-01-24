package com.iljin.apiServer.core.security.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey> {
    List<UserRole> findByRole(String role);
    List<UserRole> findAll();
    Optional<UserRole> findById(UserRoleKey userRoleKey);
    List<UserRole> findRolesByUser_LoginId(String loginId);
    Optional<UserRole> findRoleByUser_LoginId(String loginId);
    List<UserRole> findRolesByUser_UserName(String userName);

    void deleteByUserId(Long userId);

    Optional<UserRole> findByUserId(Long userId);
}
