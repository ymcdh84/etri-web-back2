package com.iljin.apiServer.core.security.user;

import com.iljin.apiServer.core.security.role.UserRole;
import com.iljin.apiServer.template.system.emp.Employee;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "A_USER")
public class User {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "session_p_id")
    String sessionPId;

    @Column(name = "login_id")
    String loginId;

    @Column(name = "login_pw")
    @NotEmpty
    @ToString.Exclude
    String loginPw;

    @Column(name = "comp_cd")
    String compCd;

    @Column(name = "dept_cd")
    String deptCd;

    @Column(name = "user_name")
    String userName;

    @Column(name = "enable_flag")
    boolean enableFlag;

    @Column(name = "attribute_1")
    String attribute1;

    @Column(name = "attribute_2")
    String attribute2;

    @Column(name = "attribute_3")
    String attribute3;

    @Column(name = "attribute_4")
    String attribute4;

    @Column(name = "attribute_5")
    String attribute5;

    @Column(name = "created_by")
    Long createdBy;

    @Column(name = "creation_date", insertable = false, updatable = false)
    LocalDateTime creationDate;

    @Column(name = "modified_by")
    Long modifiedBy;

    @Column(name = "modified_date", insertable = false, updatable = false)
    LocalDateTime modifiedDate;

    @OneToMany
    @JoinColumn(name = "user_id")
    List<UserRole> roles;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "login_id", referencedColumnName = "emp_no", insertable=false, updatable=false),
            @JoinColumn(name = "comp_cd", referencedColumnName = "comp_cd", insertable=false, updatable=false)
    })
    private Employee employee;

    @Builder
    public User updateUserRoles(List<UserRole> roles) {
        this.roles = roles;

        return this;
    }

    @Builder
    public User updateAttribute1(String attribute1) {
        this.attribute1 = attribute1;

        return this;
    }

    public User updateUserDetail(String userName, String deptCd, boolean enableFlag) {
        this.userName = userName;
        this.deptCd = deptCd;
        this.enableFlag = enableFlag;

        return this;
    }

    @Builder
    public User newUser(String loginId, @NotEmpty String loginPw, String compCd, String deptCd, String userName, boolean enableFlag, Long createdBy) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.compCd = compCd;
        this.deptCd = deptCd;
        this.userName = userName;
        this.enableFlag = enableFlag;
        this.createdBy = createdBy;

        return this;
    }
}
