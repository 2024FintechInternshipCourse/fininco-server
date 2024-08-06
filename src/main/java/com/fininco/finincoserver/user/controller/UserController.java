package com.fininco.finincoserver.user.controller;

import com.fininco.finincoserver.global.auth.Auth;
import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.user.auth.oAuth.dto.SignInResponse;
import com.fininco.finincoserver.user.auth.oAuth.service.OAuthService;
import com.fininco.finincoserver.user.dto.UserProfileResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final OAuthService OAuthService;

    @GetMapping("/kakao/callback")
    public ResponseEntity<SignInResponse> signIn(@RequestParam String code) {
        SignInResponse response = OAuthService.signIn(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<UserProfileResponse> getUserProfile(@Auth UserInfo userInfo){
        UserProfileResponse response = UserProfileResponse.from(userInfo.user());

        return ResponseEntity.ok(response);
    }


}
