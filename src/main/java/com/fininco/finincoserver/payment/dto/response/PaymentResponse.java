package com.fininco.finincoserver.payment.dto.response;

import com.fininco.finincoserver.payment.entity.Payment;
import com.fininco.finincoserver.payment.entity.PaymentType;
import com.fininco.finincoserver.point.entity.CurrencyCode;

import java.math.BigDecimal;

public record PaymentResponse(
        long id,
        BigDecimal price,
        String store,
        PaymentType paymentType,
        String userId, // User 식별번호
        Long walletId, // Wallet 식별번호
        CurrencyCode currencyCode,
        BigDecimal balance // 잔액
) {
    public static PaymentResponse from(Payment payment, BigDecimal balance) {
        return new PaymentResponse(
                payment.getId(),
                payment.getPrice(),
                payment.getStore(),
                payment.getPaymentType(),
                payment.getUser().getId(),
                payment.getWallet().getId(),
                payment.getCurrencyCode(),
                balance // 지갑의 잔액
        );
    }
}
