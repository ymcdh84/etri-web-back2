package com.iljin.apiServer.core.security.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iljin.apiServer.core.security.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "A_USER_ROLE")
@IdClass(UserRoleKey.class)
public class UserRole {
    @Id
    Long id;

    @Column(name = "user_id", insertable = false, updatable = false)
    Long userId;

    @Id
    @Column(name = "comp_cd")
    String compCd;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;

    //    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    String role;

    @Column(name = "created_by")
    Long createdBy;

    @Column(name = "creation_date", insertable = false, updatable = false)
    LocalDateTime creationDate;

    @Column(name = "modified_by")
    Long modifiedBy;

    @Column(name = "modified_date", insertable = false, updatable = false)
    LocalDateTime modifiedDate;

    @Builder
    public UserRole(Long id, String compCd, String role, User user) {
        this.id = id;
        this.compCd = compCd;
        this.role = role;
        this.user = user;
    }

    public UserRole updateRole(String role) {
        this.role = role;
        return this;
    }

    public UserRole removeUserRole() {
        this.userId = null;

        return this;
    }
}
