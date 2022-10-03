package com.example.user.client.service;

import com.example.user.client.MailgunClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private MailgunClient MailgunClient;

    @Test
    public void EmailTest() {
        //given
        MailgunClient.sendMail(null);
        //when
        //then

    }

}