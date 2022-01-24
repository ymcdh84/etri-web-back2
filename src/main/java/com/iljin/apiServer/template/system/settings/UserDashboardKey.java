package com.iljin.apiServer.template.system.settings;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDashboardKey implements Serializable {
    String loginId;
    String assetId;
}
