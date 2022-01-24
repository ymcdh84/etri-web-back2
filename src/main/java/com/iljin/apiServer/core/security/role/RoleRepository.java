package com.iljin.apiServer.core.security.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, RoleKey> {
    List<Role> findByRoleCdIn(List<String> roles);
}
