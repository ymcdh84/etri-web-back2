package com.iljin.apiServer.core.mail;

public interface MailService {
    void sendSimpleMessage(String from,
                           String to,
                           String subject,
                           String text);

    void sendSimpleMessage(MailDto mailDto);
}
