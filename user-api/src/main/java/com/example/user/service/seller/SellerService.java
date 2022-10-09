package com.example.user.service.seller;

import static com.example.user.exception.ErrorCode.ALREADY_VERIFY;
import static com.example.user.exception.ErrorCode.EXPIRE_CODE;
import static com.example.user.exception.ErrorCode.NOT_FOUND_USER;
import static com.example.user.exception.ErrorCode.WRONG_VERIFICATION;

import com.example.user.domain.SignUpForm;
import com.example.user.domain.model.Seller;
import com.example.user.domain.repository.SellerRepository;
import com.example.user.exception.CustomException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public Optional<Seller> findByAndEmail(Long id, String email) {
        return sellerRepository.findByIdAndEmail(id, email);
    }

    public Optional<Seller> findByIdAndEmail(Long id, String email){
        return sellerRepository.findById(id).stream().filter(
                customer-> customer.getEmail().equals(email))
            .findFirst();
    }

    public Optional<Seller> findValidSeller(String email, String password) {
        return sellerRepository.findByEmailAndPasswordAndVerifyIsTrue(email, password);
    }

    public Seller signUp(SignUpForm form) {
        return sellerRepository.save(Seller.from(form));
    }

    public boolean isEmailExist(String email) {
        return sellerRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code) {

        Seller seller = sellerRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if (seller.isVerify()) {
            throw new CustomException(ALREADY_VERIFY);
        } else if (!seller.getVerificationCode().equals(code)) {
            throw new CustomException(WRONG_VERIFICATION);
        } else if (seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(EXPIRE_CODE);
        }

        seller.setVerify(true);

    }

    @Transactional
    public LocalDateTime changeSellerValidateEmail(Long sellerId, String verificationCode) {
        Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);

        if (optionalSeller.isPresent()) {
            Seller seller = optionalSeller.get();
            seller.setVerificationCode(verificationCode);
            seller.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));

            return seller.getVerifyExpiredAt();
        }
        //예외 처리
        throw new CustomException(NOT_FOUND_USER);
    }

}
