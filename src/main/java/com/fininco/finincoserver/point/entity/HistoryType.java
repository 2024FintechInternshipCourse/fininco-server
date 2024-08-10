package com.fininco.finincoserver.point.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum HistoryType {

    CHARGE("충전"),
    COST("환전");

    private final String historytype;
}
