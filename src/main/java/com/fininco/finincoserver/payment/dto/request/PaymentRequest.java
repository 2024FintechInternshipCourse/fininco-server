package com.fininco.finincoserver.payment.dto.request;

import com.fininco.finincoserver.payment.entity.Payment;
import com.fininco.finincoserver.payment.entity.PaymentType;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.user.entity.User;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRequest(
        BigDecimal price,
        String store,
        PaymentType paymentType,
        String userId,
        String walletId
) {
}
