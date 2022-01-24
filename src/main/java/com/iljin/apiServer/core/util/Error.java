package com.iljin.apiServer.core.util;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Error {
    private int code;
    private String message;

    @Builder
    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
