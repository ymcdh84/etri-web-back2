package com.iljin.apiServer.core.mPush;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PushMessage {
    private String fcmToken;
    private String title;

    @Builder PushMessage(String fcmToken, String title) {
        this.fcmToken = fcmToken;
        this.title = title;
    }
}
