package com.fininco.finincoserver.point.dto.response;

import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.Wallet;

public record WalletResponse(
        CurrencyCode currencyCode,
        java.math.BigDecimal balance
) {

    public static WalletResponse from(Wallet wallet) {
        return new WalletResponse(wallet.getCurrencyCode(), wallet.getBalance());
    }

}
