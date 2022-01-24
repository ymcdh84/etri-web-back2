package com.iljin.apiServer.core.security.role;

import com.iljin.apiServer.core.security.user.UserDto;
import com.iljin.apiServer.core.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService{
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    @Override
    public List<UserRole> getAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public void addRole(UserDto userDto) {
        List<UserRole> newRoles = new ArrayList<>();

        userRepository.findById(userDto.getId())
                .ifPresent(
                        c -> c.updateUserRoles(newRoles)
                );
    }

    @Override
    public Optional<UserRole> getRoleById(UserRoleDto userRoleDto) {
        UserRoleKey userRoleKey = new UserRoleKey(userRoleDto.getId(), userRoleDto.getCompCd());

        return userRoleRepository.findById(userRoleKey);
    }

    @Override
    public List<UserRole> getRole(String role) {
        return userRoleRepository.findByRole(role);
    }

    @Override
    public List<UserRole> getRolesByLoginIdContains(String loginId) {
        return userRoleRepository.findRolesByUser_LoginId(loginId);
    }

    @Override
    public List<UserRole> getRolesByUser_UserName(String userName) {
        return userRoleRepository.findRolesByUser_UserName(userName);
    }

    @Override
    public void deleteRoleById(UserRoleDto userRoleDto) {
        UserRoleKey userRoleKey = new UserRoleKey(userRoleDto.getId(), userRoleDto.getCompCd());

        try {
            userRoleRepository.deleteById(userRoleKey);
        } catch (EmptyResultDataAccessException e) {
            log.info("There is no data... UserRoleKey is { id:" +userRoleDto.getId()+ ", compCd:" +userRoleDto.getCompCd()+ "}");
        }
    }

}
