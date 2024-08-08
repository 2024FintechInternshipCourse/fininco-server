package com.fininco.finincoserver.user.auth.oAuth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fininco.finincoserver.user.auth.oAuth.dto.KakaoAccount;
import com.fininco.finincoserver.user.entity.User;

import java.time.LocalDateTime;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoUserInfoResponse(
        String id,
        KakaoAccount kakaoAccount
) {

    public User toUserEntity() {
        return User.builder()
                .id(id)
                .nickname(kakaoAccount.profile().nickname())
                .build();
    }

}
