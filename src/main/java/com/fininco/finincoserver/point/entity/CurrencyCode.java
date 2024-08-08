package com.fininco.finincoserver.point.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CurrencyCode {

    KRW("대한민국"),
    USD("미국"),
    JPY("일본");

    private final String country;

}
