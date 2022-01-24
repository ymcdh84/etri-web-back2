package com.iljin.apiServer.core.security.loginHistory;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_user_login_history")
public class LoginHistory {
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long logId;

    @Column(name = "connect_id")
    String connectId;

    @Column(name = "connect_ip")
    String connectIp;

    @Column(name = "connect_mthd")
    String connectMthd;

    @Column(name = "connect_error")
    String connectError;

    @Column(name = "connect_url")
    String connectUrl;

    @CreatedDate
    @Column(name = "creation_date")
    LocalDateTime creationDate;

    @Builder
    public LoginHistory(String connectId, String connectIp, String connectMthd, String connectError, String connectUrl, LocalDateTime creationDate) {
        this.connectId = connectId;
        this.connectIp = connectIp;
        this.connectMthd = connectMthd;
        this.connectError = connectError;
        this.connectUrl = connectUrl;
        this.creationDate = creationDate;
    }
}
