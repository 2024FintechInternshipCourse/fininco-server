package com.fininco.finincoserver.user.auth.oAuth.client;

public interface OAuthClient {

    Platform getPlatform();

    String getAccessToken(String authCode);

    KakaoUserInfoResponse getPlatformUserInfo(String accessToken);
}
