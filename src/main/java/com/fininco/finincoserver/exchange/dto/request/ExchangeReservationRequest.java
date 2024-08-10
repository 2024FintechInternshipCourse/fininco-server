package com.fininco.finincoserver.exchange.dto.request;

import com.fininco.finincoserver.exchange.entity.ExchangeReservation;
import com.fininco.finincoserver.exchange.entity.ExchangeType;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.user.entity.User;

import java.math.BigDecimal;

public record ExchangeReservationRequest(
    ExchangeType type,
    CurrencyCode currencyCode,
    BigDecimal targetRate,
    BigDecimal beforeAmount,
    BigDecimal afterAmount
) {
    public ExchangeReservation toEntity(Wallet withdraw, Wallet deposit) {
        return ExchangeReservation.builder()
                .type(this.type)
                .currencyCode(this.currencyCode)
                .targetRate(this.targetRate)
                .withdrawWallet(withdraw)
                .depositWallet(deposit)
                .beforeAmount(this.beforeAmount)
                .afterAmount(this.afterAmount)
                .build();
    }

}
