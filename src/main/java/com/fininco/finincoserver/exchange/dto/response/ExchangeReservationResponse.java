package com.fininco.finincoserver.exchange.dto.response;

import com.fininco.finincoserver.exchange.entity.ExchangeReservation;
import com.fininco.finincoserver.exchange.entity.ExchangeResult;
import com.fininco.finincoserver.exchange.entity.ExchangeType;
import com.fininco.finincoserver.point.entity.CurrencyCode;

public record ExchangeReservationResponse(
        ExchangeType type,
        CurrencyCode currencyCode,
        long targetRate,
        long beforeAmount,
        long afterAmount,
        ExchangeResult result
) {

    public static ExchangeReservationResponse from(ExchangeReservation entity) {
        return new ExchangeReservationResponse(
                entity.getType(),
                entity.getCurrencyCode(),
                entity.getTargetRate(),
                entity.getBeforeAmount(),
                entity.getAfterAmount(),
                entity.getResult()
        );
    }

}
