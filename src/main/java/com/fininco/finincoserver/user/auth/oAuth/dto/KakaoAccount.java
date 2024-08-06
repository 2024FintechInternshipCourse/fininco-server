package com.fininco.finincoserver.user.auth.oAuth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoAccount(
        boolean profileNicknameNeedsAgreement,
        Profile profile
) {
}
