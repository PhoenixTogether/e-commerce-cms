package com.example.user.service;

import com.example.user.domain.SignUpForm;
import com.example.user.domain.model.Customer;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class SingUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService service;

    @Test
    void signUp() {
        //given
        SignUpForm form = SignUpForm.builder()
            .email("abc@gmail.com")
            .name("name")
            .password("1")
            .phone("01011112222")
            .birth(LocalDate.now())
            .build();
        //when
        //then
        Customer c = service.signUp(form);
        Assert.isTrue(service.signUp(form).getId() != null);
    }
}