package com.fininco.finincoserver.user.auth.oAuth.dto;

public record SignInResponse(
        String id,
        String accessToken
) {
    public static SignInResponse of(String id, String accessToken) {

        return new SignInResponse(id, accessToken);
    }

}
