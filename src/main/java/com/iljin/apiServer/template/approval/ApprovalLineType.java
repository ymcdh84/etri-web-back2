package com.iljin.apiServer.template.approval;

import com.iljin.apiServer.core.util.Pair;
import lombok.Getter;

public enum ApprovalLineType {
    DRAFT("10", "기안"),
    APPRV("20", "결재"),
    AGREE("30", "합의");

    @Getter
    final private String code;
    @Getter
    final private String name;

    ApprovalLineType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    Pair<String, String> getCodeAndValue() {
        return new Pair<>(code, name);
    }
}
