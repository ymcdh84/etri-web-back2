package com.iljin.apiServer.core.mPush;

import com.iljin.apiServer.template.approval.ApprPushMessage;

public interface MobilePushService {
    /* Example... */
    void pushMessage(PushMessage pushMessage);

    void pushApprMessage(ApprPushMessage apprPushMessage);
}
