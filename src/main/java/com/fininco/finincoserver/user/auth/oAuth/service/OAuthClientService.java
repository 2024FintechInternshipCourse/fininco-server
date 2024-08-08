package com.fininco.finincoserver.user.auth.oAuth.service;

import com.fininco.finincoserver.user.auth.oAuth.dto.KakaoUserInfoResponse;
import com.fininco.finincoserver.user.auth.oAuth.client.OAuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OAuthClientService {

    private final OAuthClient kakaoOAuthClient;

    public KakaoUserInfoResponse getUserInfo(String authCode) {

        String accessToken = kakaoOAuthClient.getAccessToken(authCode);
        return kakaoOAuthClient.getPlatformUserInfo(accessToken);
    }

    @Autowired
    public OAuthClientService(OAuthClient kakaoOAuthClient) {
        this.kakaoOAuthClient = kakaoOAuthClient;
    }

}
