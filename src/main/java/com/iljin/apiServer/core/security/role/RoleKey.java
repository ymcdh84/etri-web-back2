package com.iljin.apiServer.core.security.role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class RoleKey implements Serializable {
    private String compCd;
    private String roleCd;

    @Builder
    public RoleKey(String compCd, String roleCd) {
        this.compCd = compCd;
        this.roleCd = roleCd;
    }
}
