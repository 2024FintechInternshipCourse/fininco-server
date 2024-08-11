package com.fininco.finincoserver.payment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
    // 식비 숙박 교통 기타
    FOOD("식비"),
    ACOM("숙박"),
    TRANS("교통"),
    ETC("기타");
    private final String PaymentType;
}
