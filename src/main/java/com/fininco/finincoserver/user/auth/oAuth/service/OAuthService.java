package com.fininco.finincoserver.user.auth.oAuth.service;

import com.fininco.finincoserver.user.auth.domain.JwtProvider;
import com.fininco.finincoserver.user.auth.oAuth.client.KakaoUserInfoResponse;
import com.fininco.finincoserver.user.auth.oAuth.dto.SignInResponse;
import com.fininco.finincoserver.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final OAuthClientService oAuthClientService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


    public SignInResponse signIn(String authCode) {
        KakaoUserInfoResponse userInfo;
        userInfo = oAuthClientService.getUserInfo(authCode);

        if (!userRepository.existsById(userInfo.id())) {
            String userId = signUp(userInfo);
        }

        User user = userRepository.getById(userInfo.id());

        String accessToken = jwtProvider.provideAccessToken(user);

        return SignInResponse.of(user.getId(), accessToken);
    }

    public String signUp(KakaoUserInfoResponse userInfo) {
        User user = userInfo.toUserEntity();
        return userRepository.save(user).getId();
    }

}
