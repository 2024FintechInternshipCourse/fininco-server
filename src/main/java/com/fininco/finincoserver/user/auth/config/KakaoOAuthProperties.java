package com.fininco.finincoserver.user.auth.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "oauth.kakao")
public class KakaoOAuthProperties {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String responseType;
}
