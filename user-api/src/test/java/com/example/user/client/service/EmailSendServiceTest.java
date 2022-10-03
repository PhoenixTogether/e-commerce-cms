package com.example.user.client.service;

import com.example.user.config.FeignConfig;
import com.example.user.service.EmailSendService;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private EmailSendService emailSendService;

    @Test
    public void EmailTest() {
        //given
        String response = emailSendService.sendEmail();
        //when
        //then
        System.out.println(response);
    }

}