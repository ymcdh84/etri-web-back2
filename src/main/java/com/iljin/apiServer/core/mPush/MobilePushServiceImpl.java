package com.iljin.apiServer.core.mPush;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.iljin.apiServer.template.approval.ApprPushMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
public class MobilePushServiceImpl implements MobilePushService {
    /*
     * @Value("classpath:ij-eas-415a0-firebase-adminsdk-88yn0-a3c441acba.json")
     * Resource firebaseAdminSDKKey;
     */
    ClassPathResource classPathResource = new ClassPathResource("ij-eas-415a0-firebase-adminsdk-88yn0-a3c441acba.json");

    @PostConstruct
    public void postConstruct() throws IOException {

        // FileInputStream serviceAccount = new
        // FileInputStream(firebaseAdminSDKKey.getFile());

        FirebaseOptions options = new FirebaseOptions.Builder()
                // .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setCredentials(GoogleCredentials.fromStream(classPathResource.getInputStream()))
                .setDatabaseUrl("https://ij-eas-415a0.firebaseio.com").build();

        FirebaseApp.initializeApp(options);
        log.info("firebaseAdminSDK 초기화 완료");
    }

    @Override
    public void pushMessage(PushMessage pushMessage) {
        try {
            if (StringUtils.isEmpty(pushMessage.getFcmToken())) {
                log.info("token값이 없어서 fcm push 전송 안함");
                return;
            }

            Message message = Message.builder()
                    .setNotification(new Notification(pushMessage.getTitle(), "Body"))
                    .putData("data", "Data").setToken(pushMessage.getFcmToken()).build();

            ApiFuture<String> future = FirebaseMessaging.getInstance().sendAsync(message);
            log.info("fcm Push 전송 요청 data:{}", "Data");
        } catch (Exception e) {
            log.error("fcm push 전송 요청 중 에러 발생 data:{}", "Data");
        }
    }

    @Override
    public void pushApprMessage(ApprPushMessage apprPushMessage) {
        try {
            if (StringUtils.isEmpty(apprPushMessage.getFcmToken())) {
                log.info("token값이 없어서 fcm push 전송 안함");
                return;
            }

            Message message = Message.builder()
                    .setNotification(new Notification(apprPushMessage.getTitle(), apprPushMessage.getDraftUser()))
                    .putData("apprNo", apprPushMessage.getApprNo()).setToken(apprPushMessage.getFcmToken()).build();

            ApiFuture<String> future = FirebaseMessaging.getInstance().sendAsync(message);
            log.info("fcm push 전송 요청 apprNo:{}", apprPushMessage.getApprNo());
        } catch (Exception e) {
            log.error("fcm push 전송 요청 중 에러 발생 apprNo:{}", apprPushMessage.getApprNo());
        }
    }
}
