package com.fininco.finincoserver.payment.service;
import ch.qos.logback.classic.Logger;
import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.payment.dto.request.PaymentRequest;
import com.fininco.finincoserver.payment.dto.response.PaymentResponse;
import com.fininco.finincoserver.payment.entity.Payment;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.point.repository.WalletRepository;
import com.fininco.finincoserver.payment.repository.PaymentRepository;
import com.fininco.finincoserver.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final WalletRepository walletRepository;
    private Logger log;

    /**
     * 통화코드를 바탕으로 현재 wallet 금액에서 계산 수행
     */

    @Transactional
    public PaymentResponse processPayment(UserInfo userInfo, PaymentRequest paymentRequest){
        // 사용자 별로 해당 통화에 대한 지갑은 무조건 존재함
        User user = userInfo.user();
        Wallet wallet = walletRepository.findByUserAndCurrencyCode(userInfo.user(), paymentRequest.currencyCode());
        wallet.usePoint(paymentRequest.price());

        // 결제 정보
        Payment payment = paymentRequest.toEntity(user, wallet);
        paymentRepository.save(payment);

        // 결제 후 잔액 반환
        BigDecimal nowBalance = wallet.getBalance();

        System.out.println("결제 완료");

        return PaymentResponse.from(payment, nowBalance);
    }

}
