package com.fininco.finincoserver.user.auth.oAuth.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fininco.finincoserver.user.auth.oAuth.dto.KakaoAccount;
import com.fininco.finincoserver.user.entity.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

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
