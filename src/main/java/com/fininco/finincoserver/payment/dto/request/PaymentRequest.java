package com.fininco.finincoserver.payment.dto.request;

import com.fininco.finincoserver.payment.entity.Payment;
import com.fininco.finincoserver.payment.entity.PaymentType;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.user.entity.User;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRequest(
        BigDecimal price,
        String store,
        PaymentType paymentType,
        CurrencyCode currencyCode
) {
    public Payment toEntity(User user, Wallet wallet) {
        return Payment.builder()
                .price(price)
                .store(store)
                .paymentType(paymentType)
                .user(user)
                .wallet(wallet)
                .currencyCode(this.currencyCode)
                .build();
    }
}
