package com.iljin.apiServer.template.mail;

import com.iljin.apiServer.core.mail.MailDto;
import com.iljin.apiServer.core.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MailController {
    private final MailService mailService;

    @PostMapping("/mail")
    void sendEmail(@RequestBody MailDto mailDto) {
        mailService.sendSimpleMessage(mailDto);
    }
}
