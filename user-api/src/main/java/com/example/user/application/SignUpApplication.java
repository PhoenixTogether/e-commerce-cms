package com.example.user.application;

import com.example.user.client.MailgunClient;
import com.example.user.client.mailgun.SendMailForm;
import com.example.user.domain.SignUpForm;
import com.example.user.domain.model.Customer;
import com.example.user.domain.model.Seller;
import com.example.user.exception.CustomException;
import com.example.user.exception.ErrorCode;
import com.example.user.service.customer.SignUpCustomerService;
import com.example.user.service.seller.SellerService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SellerService sellerService;

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public void sellerVerify(String email, String code) {
        sellerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer c = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();

            SendMailForm sendMailForm = SendMailForm.builder()
                .from("kico@kico.co.jp")
                .to(form.getEmail())
                .subject("Verification Email")
                .text(getVerificationEmailBody(form.getEmail(), form.getName(), "customer", code))
                .build();

            mailgunClient.sendMail(sendMailForm);
            signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);

            return "회원가입에 성공하였습니다.";

        }
    }

    public String sellerSignUp(SignUpForm form) {
        if (sellerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Seller s = sellerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();

            SendMailForm sendMailForm = SendMailForm.builder()
                .from("kico@kico.co.jp")
                .to(form.getEmail())
                .subject("Verification Email")
                .text(getVerificationEmailBody(form.getEmail(), form.getName(), "seller", code))
                .build();

            mailgunClient.sendMail(sendMailForm);
            sellerService.changeSellerValidateEmail(s.getId(), code);

            return "회원가입에 성공하였습니다.";

        }
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello").append(name)
            .append("! Please Click Link for verification.\n\n")
            .append("http://localhost:8081/signup/" + type + "/verify/?email=")
            .append(email)
            .append("&code=")
            .append(code).toString();
    }
}
