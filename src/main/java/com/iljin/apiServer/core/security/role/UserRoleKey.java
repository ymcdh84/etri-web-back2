package com.iljin.apiServer.core.security.role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class UserRoleKey implements Serializable {
    Long id;
    String compCd;

    @Builder
    public UserRoleKey(Long id, String compCd) {
        this.id = id;
        this.compCd = compCd;
    }
}
