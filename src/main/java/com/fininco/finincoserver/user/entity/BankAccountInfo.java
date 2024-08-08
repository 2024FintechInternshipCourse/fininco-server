package com.fininco.finincoserver.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankAccountInfo implements Serializable {

    @Column(name = "bank")
    private Bank bank;

    @Column(name = "account_no")
    private String accountNo;

    @Builder
    public BankAccountInfo(Bank bank, String accountNo) {
        this.bank = bank;
        this.accountNo = accountNo;
    }

}
