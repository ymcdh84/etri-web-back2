package com.iljin.apiServer.core.security.role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
    Long id;
    Long userId;
    String role;
    String roleType;
    Long createdBy;
}
