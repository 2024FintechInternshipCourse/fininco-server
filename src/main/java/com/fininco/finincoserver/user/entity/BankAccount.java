package com.fininco.finincoserver.user.entity;

import com.fininco.finincoserver.global.entity.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "bank_accounts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankAccount extends BaseEntity {

    @EmbeddedId
    private BankAccountInfo info;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public BankAccount(BankAccountInfo info, User user) {
        this.info = info;
        this.user = user;
    }

}
