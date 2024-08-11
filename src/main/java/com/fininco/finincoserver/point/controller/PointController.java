package com.fininco.finincoserver.point.Controller;


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
    public ResponseEntity<PointHistoryResponse> chargePoint(@RequestBody PointHistoryCreateRequest request) {
        PointHistoryResponse response = pointService.chargePoint(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 포인트 내역 조회
     * @param userId 조회할 사용자 ID
     * @param period 조회할 기간 (기본값: 오늘날짜로 한달)
     * @return 포인트 내역 목록
     */

    @GetMapping("/history")
    public ResponseEntity<List<PointHistoryResponse>> getPointHistories(
            @RequestParam String userId,
            @RequestParam(required = false, defaultValue = "1month") String period
    ) {
        List<PointHistoryResponse> response = pointService.getPointHistories(userId, period);
        return ResponseEntity.ok(response);
    }
}

