package com.fininco.finincoserver.payment.entity;

import com.fininco.finincoserver.global.entity.BaseEntity;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Payment")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 금액
    private BigDecimal price;

    // 결제처
    private String store;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    public Payment(Long id, BigDecimal price, String store, PaymentType paymentType, User user, Wallet wallet) {
        this.id = id;
        this.price = price;
        this.store = store;
        this.paymentType = paymentType;
        this.user = user;
        this.wallet = wallet;
    }
}
