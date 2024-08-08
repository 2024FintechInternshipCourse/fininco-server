package com.fininco.finincoserver.user.controller;

import com.fininco.finincoserver.global.auth.Auth;
import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.user.dto.request.BankAccountCreateRequest;
import com.fininco.finincoserver.user.dto.response.BankAccountResponse;
import com.fininco.finincoserver.user.dto.response.SignInResponse;
import com.fininco.finincoserver.user.dto.response.UserProfileResponse;
import com.fininco.finincoserver.user.service.UserAuthService;
import com.fininco.finincoserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserAuthService UserAuthService;
    private final UserService userService;

    @GetMapping("/kakao/callback")
    public ResponseEntity<SignInResponse> signIn(@RequestParam String code) {
        SignInResponse response = UserAuthService.signIn(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<UserProfileResponse> getUserProfile(@Auth UserInfo userInfo) {
        UserProfileResponse response = UserProfileResponse.from(userInfo.user());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/account")
    public ResponseEntity<BankAccountResponse> createBankAccount(@Auth UserInfo userInfo, BankAccountCreateRequest request) {
        BankAccountResponse response = userService.createBankAccount(userInfo, request);

        return ResponseEntity.ok(response);
    }


}
