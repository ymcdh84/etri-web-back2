package com.iljin.apiServer.template.approval;

import com.iljin.apiServer.core.util.Pair;
import lombok.Getter;

public enum ApprovalType {
    TYPE_SLIP("SLIP", "전표"),
    TYPE_BUDGET("BDGT", "예산");

    @Getter
    final private String code;
    @Getter
    final private String name;

    ApprovalType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    Pair<String, String> getCodeAndValue() {
        return new Pair<>(code, name);
    }
}
