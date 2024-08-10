package com.fininco.finincoserver.point.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void chargePoint(BigDecimal point) {
        balance = balance.add(point);
    }

    public void usePoint(BigDecimal point) {
        log.info("비교: {}", this.balance.subtract(point).compareTo(BigDecimal.ZERO));
        log.info("잔여: {}", this.balance.subtract(point));
        if (this.balance.subtract(point).compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("잔액이 부족합니다.");
        }

        this.balance = balance.subtract(point);
    }

    public Wallet(CurrencyCode currencyCode, User user) {
        this.balance = BigDecimal.ZERO;
        this.currencyCode = currencyCode;
        this.user = user;
    }

}
