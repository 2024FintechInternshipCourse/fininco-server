package com.fininco.finincoserver.user.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class AccountInfo{

    private Bank bank;

    private String accountNo;

}
