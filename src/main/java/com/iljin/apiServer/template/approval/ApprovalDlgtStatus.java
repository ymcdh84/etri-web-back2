package com.iljin.apiServer.template.approval;

import com.iljin.apiServer.core.util.Pair;
import lombok.Getter;

public enum ApprovalDlgtStatus {
    DELEGATING("10", "위임"),
    CANCEL("20", "지정해제"),
    EXPIRATION("30", "기간해제");

    @Getter
    final private String code;
    @Getter
    final private String name;

    ApprovalDlgtStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    Pair<String, String> getCodeAndName() { return new Pair<>(code, name); }
}
