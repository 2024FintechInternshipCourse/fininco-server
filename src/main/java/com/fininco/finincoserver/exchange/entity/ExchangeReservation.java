package com.fininco.finincoserver.exchange.entity;


import com.fininco.finincoserver.global.entity.BaseEntity;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeReservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    ExchangeType type;

    CurrencyCode currencyCode;

    long targetRate;

    long beforeAmount;

    long afterAmount;

    ExchangeResult result;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Builder

    public ExchangeReservation(ExchangeType type, CurrencyCode currencyCode, long targetRate, long beforeAmount, long afterAmount, ExchangeResult result, User user) {
        this.type = type;
        this.currencyCode = currencyCode;
        this.targetRate = targetRate;
        this.beforeAmount = beforeAmount;
        this.afterAmount = afterAmount;
        this.result = ExchangeResult.PENDING;
        this.user = user;
    }
}
