package com.iljin.apiServer.template.security;

import com.iljin.apiServer.core.security.role.RoleService;
import com.iljin.apiServer.core.security.role.RoleType;
import com.iljin.apiServer.core.security.role.UserRole;
import com.iljin.apiServer.core.security.role.UserRoleDto;
import com.iljin.apiServer.core.security.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/roles")
    public List<UserRole> getAll() {
        return roleService.getAll();
    }

    @PostMapping("/add")
    public void addRole(@RequestBody UserDto userDto) {
        roleService.addRole(userDto);
    }

    @PostMapping(value = "/user/role-key")
    public Optional<UserRole> getRoleById(@RequestBody UserRoleDto userRoleDto) {
        return roleService.getRoleById(userRoleDto);
    }

    @GetMapping(value = "/by-role/{role}")
    public List<UserRole> getRole(@PathVariable String role) {
        return roleService.getRole(role);
    }

    @GetMapping(value = "/login-id/{loginId}")
    public List<UserRole> getRolesByLoginIdContains(@PathVariable String loginId){
        return roleService.getRolesByLoginIdContains(loginId);
    }

    @GetMapping(value = "/user-name/{userName}")
    public List<UserRole> getRolesByUser_UserName(@PathVariable String userName) {
        return roleService.getRolesByUser_UserName(userName);
    }

    @PostMapping(value = "/delete")
    public void deleteRoleById(@RequestBody UserRoleDto userRoleDto) {
        roleService.deleteRoleById(userRoleDto);
    }

}
