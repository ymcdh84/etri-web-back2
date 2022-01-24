package com.iljin.apiServer.template.approval;

import lombok.*;

@Getter
@Setter
public class ApprPushMessage {
    private String fcmToken;
    private String title;
    private String draftUser;
    private String apprNo;
}
