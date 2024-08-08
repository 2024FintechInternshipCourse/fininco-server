package com.fininco.finincoserver.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bank {

    B001("KB국민은행"),
    B002("우리은행"),
    B003("기업은행");


    private final String name;

}
