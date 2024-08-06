package com.fininco.finincoserver.global.auth;

import io.jsonwebtoken.Claims;

public record Authentication(
        String userId
) {

    public static Authentication from(Claims claims) {
        String userId = claims.getSubject();
        return new Authentication(userId);
    }

}
