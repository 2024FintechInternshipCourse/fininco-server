package com.fininco.finincoserver.point.dto.response;

import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.Wallet;

import java.math.BigDecimal;

public record WalletResponse(
        CurrencyCode currencyCode,
        BigDecimal balance
) {

    public static WalletResponse from(Wallet wallet) {
        return new WalletResponse(wallet.getCurrencyCode(), wallet.getBalance());
    }

}
