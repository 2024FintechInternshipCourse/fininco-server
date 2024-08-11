package com.fininco.finincoserver.point.service;


import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.point.dto.request.PointHistoryCreateRequest;
import com.fininco.finincoserver.point.dto.response.PointHistoryResponse;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.PointHistory;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.point.repository.PointHistoryRepository;
import com.fininco.finincoserver.point.repository.WalletRepository;
import com.fininco.finincoserver.user.entity.User;
import com.fininco.finincoserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 포인트 충전, 내역 조회
 */

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointHistoryRepository pointHistoryRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    private static final CurrencyCode ALLOWED_CURRENCY = CurrencyCode.KRW;

    /**
     * 포인트 충전 처리 request.historyType() == CHARGE
     */

    public PointHistoryResponse chargePoint(UserInfo userInfo, PointHistoryCreateRequest request) {
//
//        // User 객체 조회
//        User user = userRepository.findById(request.userId())
//                .orElseThrow(() -> new IllegalArgumentException("없는 유저 입니다"));

        User user = userInfo.user();

        // Wallet 객체 조회
        Wallet wallet = walletRepository.findByUserAndCurrencyCode(user, ALLOWED_CURRENCY);

        if (wallet == null) {
            System.out.println("Wallet not found for user: " + user.getId() + " and currency: " + ALLOWED_CURRENCY);
            throw new IllegalStateException("지갑이 없습니다");

        }

        walletRepository.save(wallet);

        // PointHistory 엔티티 생성
        PointHistory pointHistory = request.toEntity(user);

        // 포인트 내역 저장
        PointHistory savedHistory = pointHistoryRepository.save(pointHistory);

        return PointHistoryResponse.from(savedHistory);
    }

    /**
     * 포인트 내역 -> 리스트
     */

    public List<PointHistoryResponse> getPointHistories(UserInfo userInfo){

//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("없는 유저 입니다"));
        User user = userInfo.user();

        List<PointHistory> histories = pointHistoryRepository.findByUser(user);

        return histories.stream()
                .map(PointHistoryResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 포인트 내역 조회 - 최신순, 1개월, 3개월 6개월
     */

    private LocalDateTime calculateFromDate(String period) {
        LocalDateTime now = LocalDateTime.now();
        return switch (period.toLowerCase()) {
            case "1month" -> now.minusMonths(1);
            case "3months" -> now.minusMonths(3);
            case "6months" -> now.minusMonths(6);
            default -> now.minusYears(10); // 모든 내역
        };
    }
}

