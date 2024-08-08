package com.fininco.finincoserver.user.dto.request;

import com.fininco.finincoserver.user.entity.Bank;
import com.fininco.finincoserver.user.entity.BankAccount;
import com.fininco.finincoserver.user.entity.BankAccountInfo;
import com.fininco.finincoserver.user.entity.User;

public record BankAccountCreateRequest(
        Bank bank,
        String accountNo
) {
    public BankAccount toEntity(User user) {
        BankAccountInfo info = BankAccountInfo.builder()
                .bank(bank)
                .accountNo(accountNo)
                .build();

        return BankAccount.builder()
                .info(info)
                .user(user)
                .build();
    }

}
