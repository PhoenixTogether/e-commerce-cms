package com.example.user.application;

import static com.example.user.exception.ErrorCode.LOGIN_CHECK_FAIL;

import com.example.user.domain.SignInForm;
import com.example.user.domain.model.Customer;
import com.example.user.exception.CustomException;
import com.example.user.service.CustomerService;
import com.zerobase.domain.common.UserType;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여부
        Customer customer = customerService.findValidCustomer(form.getEmail(),
                form.getPassword())
            .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));

        // 2. 토큰을 발행



        // 3. 토큰을 response
        return provider.createToken(customer.getEmail(), customer.getId(), UserType.CUSTOMER);
    }


}
