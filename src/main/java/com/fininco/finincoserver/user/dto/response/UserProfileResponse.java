package com.fininco.finincoserver.user.dto.response;

import com.fininco.finincoserver.user.entity.User;

public record UserProfileResponse(
        String nickname
) {
    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(user.getNickname());
    }

}
