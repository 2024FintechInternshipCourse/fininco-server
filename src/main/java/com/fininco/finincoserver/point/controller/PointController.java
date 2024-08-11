package com.fininco.finincoserver.point.controller;


import com.fininco.finincoserver.global.auth.Auth;
import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.point.dto.request.PointHistoryCreateRequest;
import com.fininco.finincoserver.point.dto.response.PointHistoryResponse;
import com.fininco.finincoserver.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    /**
     * 포인트 충전
     */
    @PostMapping("/charge")
    public ResponseEntity<PointHistoryResponse> chargePoint(@Auth UserInfo userInfo, @RequestBody PointHistoryCreateRequest request) {
        PointHistoryResponse response = pointService.chargePoint(userInfo, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 포인트 내역 조회
     * userInfo
     * @param period 조회할 기간 (기본값: 오늘날짜로 한달)
     * @return 포인트 내역 목록
     */

    @GetMapping("/history")
    public ResponseEntity<List<PointHistoryResponse>> getPointHistories(
            @Auth UserInfo userInfo,
            @RequestParam(required = false, defaultValue = "1month") String period
    ) {
        List<PointHistoryResponse> response = pointService.getPointHistories(userInfo);
        return ResponseEntity.ok(response);
    }
}

