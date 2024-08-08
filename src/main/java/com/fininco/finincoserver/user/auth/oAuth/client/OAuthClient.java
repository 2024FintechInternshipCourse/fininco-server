package com.fininco.finincoserver.user.auth.oAuth.client;

import com.fininco.finincoserver.user.auth.oAuth.dto.KakaoUserInfoResponse;

public interface OAuthClient {

    Platform getPlatform();

    String getAccessToken(String authCode);

    KakaoUserInfoResponse getPlatformUserInfo(String accessToken);
}
