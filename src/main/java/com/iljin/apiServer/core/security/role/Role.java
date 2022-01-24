package com.iljin.apiServer.core.security.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@IdClass(RoleKey.class)
@Table(name = "a_role")
public class Role {
    @Id
    @Column(name = "comp_cd")
    private String compCd;

    @Id
    @Column(name = "role_cd")
    private String roleCd;

    @Column(name = "role_nm")
    private String roleNm;

    @Column(name = "role_select_cd")
    private String roleSelectCd;

    @Column(name = "role_dc")
    @JsonIgnore
    private String roleDc;

    @Builder
    public Role (String compCd, String roleCd, String roleNm, String roleSelectCd, String roleDc) {
        this.compCd = compCd;
        this.roleCd = roleCd;
        this.roleNm = roleNm;
        this.roleSelectCd = roleSelectCd;
        this.roleDc = roleDc;
    }

    public Role updateRole(String roleNm, String roleSelectCd, String roleDc) {
        this.roleNm = roleNm;
        this.roleSelectCd = roleSelectCd;
        this.roleDc = roleDc;

        return this;
    }
}
