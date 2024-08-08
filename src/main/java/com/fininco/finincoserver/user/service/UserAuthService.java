package com.fininco.finincoserver.user.service;

import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.point.repository.WalletRepository;
import com.fininco.finincoserver.user.auth.domain.JwtProvider;
import com.fininco.finincoserver.user.auth.oAuth.dto.KakaoUserInfoResponse;
import com.fininco.finincoserver.user.auth.oAuth.service.OAuthClientService;
import com.fininco.finincoserver.user.dto.response.SignInResponse;
import com.fininco.finincoserver.user.entity.User;
import com.fininco.finincoserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final OAuthClientService oAuthClientService;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Transactional
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
        User saved = userRepository.save(user);
        List<Wallet> wallets = createInitialWallet(saved);
        walletRepository.saveAll(wallets);

        return saved.getId();
    }

    private List<Wallet> createInitialWallet(User user) {
        List<Wallet> wallets = new ArrayList<>();

        wallets.add(new Wallet(CurrencyCode.KRW, user));
        wallets.add(new Wallet(CurrencyCode.USD, user));
        wallets.add(new Wallet(CurrencyCode.JPY, user));

        return wallets;
    }

}
