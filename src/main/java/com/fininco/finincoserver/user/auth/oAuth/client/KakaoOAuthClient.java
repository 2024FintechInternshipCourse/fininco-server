package com.fininco.finincoserver.user.auth.oAuth.client;

import com.fininco.finincoserver.global.exception.AuthenticationException;
import com.fininco.finincoserver.user.auth.AuthErrorCode;
import com.fininco.finincoserver.user.auth.config.KakaoOAuthProperties;
import com.fininco.finincoserver.user.auth.oAuth.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    private static final String GRANT_TYPE = "authorization_code";
    private static final String REQUEST_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String REQUEST_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private final KakaoOAuthProperties kakaoOAuthProperties;
    private final RestTemplate restTemplate;

    @Override
    public Platform getPlatform() {
        return Platform.KAKAO;
    }

    @Override
    public String getAccessToken(String authCode) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", kakaoOAuthProperties.getClientId());
        body.add("redirect_uri", kakaoOAuthProperties.getRedirectUri());
        body.add("client_secret", kakaoOAuthProperties.getClientSecret());
        body.add("code", authCode);

        try {
            KakaoToken token = restTemplate.postForObject(
                    REQUEST_TOKEN_URL,
                    new HttpEntity<>(body, httpHeaders),
                    KakaoToken.class
            );

            if (token == null) {
                throw new AuthenticationException(AuthErrorCode.PLATFORM_TOKEN_REQUEST_FAILED);
            }

            return token.accessToken();
        } catch (HttpStatusCodeException exception) {
            throw new AuthenticationException(AuthErrorCode.PLATFORM_TOKEN_REQUEST_FAILED);
        }
    }

    @Override
    public KakaoUserInfoResponse getPlatformUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBearerAuth(accessToken);

        try {
            KakaoUserInfoResponse response = restTemplate.postForObject(
                    REQUEST_USER_INFO_URL,
                    new HttpEntity<>(httpHeaders),
                    KakaoUserInfoResponse.class
            );

            if (response == null) {
                throw new AuthenticationException(AuthErrorCode.PLATFORM_USER_REQUEST_FAILED);
            }

            return response;
        } catch (HttpStatusCodeException exception) {
            throw new AuthenticationException(AuthErrorCode.PLATFORM_USER_REQUEST_FAILED);
        }
    }

}
