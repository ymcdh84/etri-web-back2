package com.iljin.apiServer.core.security.role;

import com.iljin.apiServer.core.security.user.UserDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<UserRole> getAll();
    void addRole(UserDto dto);
    Optional<UserRole> getRoleById(UserRoleDto userRoleDto);
    List<UserRole> getRole(String role);
    List<UserRole> getRolesByLoginIdContains(String loginId);
    List<UserRole> getRolesByUser_UserName(String userName);
    void deleteRoleById(UserRoleDto userRoleDto);

}
