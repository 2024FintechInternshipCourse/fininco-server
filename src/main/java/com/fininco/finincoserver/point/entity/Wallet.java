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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long balance;

    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void chargePoint(long point) {
        balance += point;
    }

    public void costPoint(long point) {
        if (balance - point < 0) {
            throw new RuntimeException("잔액이 부족합니다.");
        }

        balance -= point;
    }

    public Wallet(CurrencyCode currencyCode, User user) {
        this.balance = 0;
        this.currencyCode = currencyCode;
        this.user = user;
    }

}
