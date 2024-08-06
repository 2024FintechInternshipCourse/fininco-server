package com.fininco.finincoserver.global.auth;

import com.fininco.finincoserver.user.entity.User;

public record UserInfo(
        User user
) {

    public static UserInfo from(AuthContext authContext) {
        return new UserInfo(authContext.getUser());
    }

    public boolean isAuthenticated() {
        return this.user != null;
    }

}
