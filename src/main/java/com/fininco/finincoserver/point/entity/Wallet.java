package com.fininco.finincoserver.point.entity;

import com.fininco.finincoserver.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
        if (balance.subtract(point).compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("잔액이 부족합니다.");
        }

        balance = balance.subtract(point);
    }

    public Wallet(CurrencyCode currencyCode, User user) {
        this.balance = BigDecimal.ZERO;
        this.currencyCode = currencyCode;
        this.user = user;
    }

}