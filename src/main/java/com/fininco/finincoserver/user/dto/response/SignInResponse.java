package com.fininco.finincoserver.user.dto.response;

public record SignInResponse(
        String id,
        String accessToken
) {
    public static SignInResponse of(String id, String accessToken) {

        return new SignInResponse(id, accessToken);
    }

}
