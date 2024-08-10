package com.fininco.finincoserver.exchange.dto.response;

import com.fininco.finincoserver.exchange.entity.ExchangeReservation;
import com.fininco.finincoserver.exchange.entity.ExchangeStatus;
import com.fininco.finincoserver.exchange.entity.ExchangeType;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ExchangeReservationResponse(
        ExchangeType type,
        CurrencyCode currencyCode,
        BigDecimal targetRate,
        BigDecimal beforeAmount,
        BigDecimal afterAmount,
        ExchangeStatus result
) {

    public static ExchangeReservationResponse from(ExchangeReservation entity) {
        return new ExchangeReservationResponse(
                entity.getType(),
                entity.getCurrencyCode(),
                entity.getTargetRate(),
                entity.getBeforeAmount(),
                entity.getAfterAmount(),
                entity.getStatus()
        );
    }

}
