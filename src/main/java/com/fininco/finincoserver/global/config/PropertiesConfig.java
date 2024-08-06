package com.fininco.finincoserver.global.config;

import com.fininco.finincoserver.user.auth.config.JwtProperties;
import com.fininco.finincoserver.user.auth.config.KakaoOAuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {KakaoOAuthProperties.class, JwtProperties.class})
public class PropertiesConfig {
}
