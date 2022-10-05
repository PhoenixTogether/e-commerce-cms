package com.example.user.service;


import static com.example.user.exception.ErrorCode.ALREADY_VERIFY;
import static com.example.user.exception.ErrorCode.EXPIRE_CODE;
import static com.example.user.exception.ErrorCode.NOT_FOUND_USER;
import static com.example.user.exception.ErrorCode.WRONG_VERIFICATION;

import com.example.user.domain.SignUpForm;
import com.example.user.domain.model.Customer;
import com.example.user.domain.repository.CustomerRepository;
import com.example.user.exception.CustomException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;

    public Customer signUp(SignUpForm form) {

        return customerRepository.save(Customer.from(form));
    }


    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }
    @Transactional
    public void verifyEmail(String email, String code) {
        Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if (customer.isVerify()) {
            throw new CustomException(ALREADY_VERIFY);
        } else if (!customer.getVerificationCode().equals(code)) {
            throw new CustomException(WRONG_VERIFICATION);
        } else if (customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(EXPIRE_CODE);
        }

        customer.setVerify(true);
    }

    @Transactional
    public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setVerificationCode(verificationCode);
            customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));

            return customer.getVerifyExpiredAt();
        }
        //예외 처리
        throw new CustomException(NOT_FOUND_USER);
    }
}
