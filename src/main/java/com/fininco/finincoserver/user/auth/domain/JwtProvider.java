package com.fininco.finincoserver.user.auth.domain;

import com.fininco.finincoserver.user.auth.config.JwtProperties;
import com.fininco.finincoserver.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final SecretKey key;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public String provideAccessToken(User user) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + jwtProperties.getAccessTokenExpiration());

        return Jwts.builder()
                .subject(user.getId())
                .issuedAt(now)
                .expiration(expiredAt)
                .signWith(key)
                .compact();
    }

}
