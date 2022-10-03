package com.example.user.service;


import com.example.user.client.MailgunClient;
import com.example.user.client.mailgun.SendMailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {

    private final MailgunClient mailgunClient;

    public String sendEmail() {

        SendMailForm form = SendMailForm.builder()
            .from("test@test.tc.jp")
            .to("sjy19910222@gmail.com")
            .subject("Test email")
            .text("My text")
            .build();
        return mailgunClient.sendMail(form).getBody();
    }

}
