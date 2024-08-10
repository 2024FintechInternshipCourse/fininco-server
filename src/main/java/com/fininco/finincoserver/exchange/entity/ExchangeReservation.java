package com.fininco.finincoserver.exchange.entity;


import com.fininco.finincoserver.global.entity.BaseEntity;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeReservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    ExchangeType type;

    @Enumerated(EnumType.STRING)
    CurrencyCode currencyCode;

    BigDecimal targetRate;

    BigDecimal beforeAmount;

    BigDecimal afterAmount;

    @Enumerated(EnumType.STRING)
    ExchangeStatus status;

    @ManyToOne
    @JoinColumn(name = "withdraw_wallet_id")
    Wallet withdrawWallet;

    @ManyToOne
    @JoinColumn(name = "deposit_wallet_id")
    Wallet depositWallet;

    @Builder
    public ExchangeReservation(ExchangeType type, CurrencyCode currencyCode, BigDecimal targetRate, BigDecimal beforeAmount, BigDecimal afterAmount, Wallet withdrawWallet, Wallet depositWallet) {
        this.type = type;
        this.currencyCode = currencyCode;
        this.targetRate = targetRate;
        this.beforeAmount = beforeAmount;
        this.afterAmount = afterAmount;
        this.status = ExchangeStatus.PENDING;
        this.withdrawWallet = withdrawWallet;
        this.depositWallet = depositWallet;
    }




    public void complete() {
        this.status = ExchangeStatus.COMPLETE;
    }

    public void failed() {
        this.status = ExchangeStatus.FAILED;
    }


}
