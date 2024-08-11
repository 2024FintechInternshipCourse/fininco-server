package com.fininco.finincoserver.payment.controller;

import com.fininco.finincoserver.global.auth.Auth;
import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.payment.dto.request.PaymentRequest;
import com.fininco.finincoserver.payment.dto.response.PaymentResponse;
import com.fininco.finincoserver.payment.service.PaymentService;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /**
     * 특정 사용자의 지갑 중, 지정된 통화 (엔화, 달러) 거래내역 결제 조회
     * @param userInfo 인증된 사용자
     * @param request 결제 요청 정보 (통화 코드 포함)
     * @return 결제 내역 목록
     */
    @GetMapping("/wallet/history")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByWallet(
            @Auth UserInfo userInfo,
            @RequestParam("currencyCode") CurrencyCode currencyCode) {

        PaymentRequest request = PaymentRequest.builder()
                .currencyCode(currencyCode)
                .build();
        List<PaymentResponse> responses = paymentService.getPaymentsByWallet(userInfo, request);
        return ResponseEntity.ok(responses);
    }
}
