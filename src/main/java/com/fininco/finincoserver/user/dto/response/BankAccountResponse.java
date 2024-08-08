package com.fininco.finincoserver.user.dto.response;

import com.fininco.finincoserver.user.entity.BankAccount;

public record BankAccountResponse(

        String bank,
        String accountNo

) {
    public static BankAccountResponse from(BankAccount bankAccount) {
        return new BankAccountResponse(
                bankAccount.getInfo().getBank().getName(),
                bankAccount.getInfo().getAccountNo()
        );
    }
}
