package com.iljin.apiServer.core.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String from;
    private String to;
    private String subject;
    private String text;
}
