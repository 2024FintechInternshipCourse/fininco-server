package com.fininco.finincoserver.exchange.dto.request;

import com.fininco.finincoserver.exchange.entity.ExchangeReservation;
import com.fininco.finincoserver.exchange.entity.ExchangeType;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.user.entity.User;

public record ExchangeReservationRequest(
    ExchangeType type,
    CurrencyCode currencyCode,
    long targetRate,
    long beforeAmount,
    long afterAmount
) {
    public ExchangeReservation toEntity(User user) {
        return ExchangeReservation.builder()
                .type(this.type)
                .currencyCode(this.currencyCode)
                .targetRate(this.targetRate)
                .beforeAmount(this.beforeAmount)
                .afterAmount(this.afterAmount)
                .user(user)
                .build();
    }

}
