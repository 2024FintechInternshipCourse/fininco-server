package com.fininco.finincoserver.point.Controller;


import com.fininco.finincoserver.point.dto.request.PointHistoryCreateRequest;
import com.fininco.finincoserver.point.dto.response.PointHistoryResponse;
import com.fininco.finincoserver.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}