package com.iljin.apiServer.template.system.settings;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="tb_user_dashboard")
@IdClass(UserDashboardKey.class)
public class UserDashboard {
    @Id
    @Column(name="LOGIN_ID")
    String loginId;

    @Id
    @Column(name="ASSET_ID")
    String assetId;

    @Column(name="ASSET_ORDER")
    Integer assetOrder;
/*
    @OneToOne
    @JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", insertable=false, updatable=false)
    private AssetHeader assetHeader;*/
}
