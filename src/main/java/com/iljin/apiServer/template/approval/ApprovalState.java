package com.iljin.apiServer.template.approval;

import com.iljin.apiServer.core.util.Pair;
import lombok.Getter;

public enum ApprovalState {
    REQUEST_APPROVAL("REQ", "결재요청"),
    PROGRESS_APPROVAL("ING", "결재진형"),
    REJECT_APPROVAL("REJ", "결재반려"),
    COMPLETED_APPROVAL("APR", "결재완료");

    @Getter
    final private String code;
    @Getter
    final private String name;

    ApprovalState(String code, String name) {
        this.code = code;
        this.name = name;
    }

    Pair<String, String> getCodeAndValue() {
        return new Pair<>(code, name);
    }
}
