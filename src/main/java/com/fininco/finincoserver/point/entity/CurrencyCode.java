package com.fininco.finincoserver.point.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum CurrencyCode {

    KRW("대한민국"),
    USD("미국"),
    JPY("일본");

    private final String country;

    public static CurrencyCode fromString(String name) {
        return CurrencyCode.valueOf(name);
    }

    public static List<CurrencyCode> getForeignCurrencyCodes() {
        return Arrays.stream(CurrencyCode.values())
                .filter(code -> !code.equals(CurrencyCode.KRW))
                .collect(Collectors.toList());
    }

}
