package com.fininco.finincoserver.payment.controller;

import com.fininco.finincoserver.global.auth.Auth;
import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.payment.dto.request.PaymentRequest;
import com.fininco.finincoserver.payment.dto.response.PaymentResponse;
import com.fininco.finincoserver.payment.service.PaymentService;
import com.fininco.finincoserver.user.entity.User;
import com.fininco.finincoserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 결제 관련 API
 */

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final UserService userService;
    /**
     * 결제 처리
     * @param request 결제 요청 정보
     * @param user 인증된 사용자
     * @return 결제 결과
     */

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@Auth UserInfo userInfo, @RequestBody PaymentRequest request) {
        // 인증된 사용자 정보를 기반으로 결제 처리
        PaymentResponse response = paymentService.processPayment(userInfo, request);
        return ResponseEntity.ok(response);
    }
}
