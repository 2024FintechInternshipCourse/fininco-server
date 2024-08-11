package com.fininco.finincoserver.exchange.service;

import com.fininco.finincoserver.exchange.dto.request.ExchangeReservationRequest;
import com.fininco.finincoserver.exchange.dto.response.ExchangeReservationResponse;
import com.fininco.finincoserver.exchange.entity.ExchangeHistory;
import com.fininco.finincoserver.exchange.entity.ExchangeRate;
import com.fininco.finincoserver.exchange.entity.ExchangeReservation;
import com.fininco.finincoserver.exchange.repository.ExchangeHistoryRepository;
import com.fininco.finincoserver.exchange.repository.ExchangeRateRepository;
import com.fininco.finincoserver.exchange.repository.ExchangeReservationRepository;
import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.point.dto.response.ExchangeBatchResponse;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.HistoryType;
import com.fininco.finincoserver.point.entity.PointHistory;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.point.repository.PointHistoryRepository;
import com.fininco.finincoserver.point.repository.WalletRepository;
import com.fininco.finincoserver.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.fininco.finincoserver.exchange.entity.ExchangeStatus.PENDING;
import static com.fininco.finincoserver.exchange.entity.ExchangeType.BUY;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeReservationRepository exchangeReservationRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final WalletRepository walletRepository;
    private final ExchangeHistoryRepository exchangeHistoryRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public ExchangeReservationResponse reserveBuy(UserInfo userInfo, ExchangeReservationRequest request) {
        User user = userInfo.user();
        ExchangeRate currentRate = exchangeRateRepository.findTopByBasedateOrderByIdDesc(LocalDate.now())
                .orElse(exchangeRateRepository.findTopByOrderById());

        Wallet withdrawWallet = walletRepository.findByUserAndCurrencyCode(user, CurrencyCode.KRW);
        Wallet depositWallet = walletRepository.findByUserAndCurrencyCode(user, request.currencyCode());
        ExchangeReservation exchangeReservation = request.toEntity(withdrawWallet, depositWallet);

        if (currentRate.getGetCurrency().equals(request.targetRate())) {
            exchangeReservation.complete();
            PointHistory pointHistory = PointHistory.builder()
                    .amount(exchangeReservation.getBeforeAmount())
                    .historyType(HistoryType.COST)
                    .user(user)
                    .build();

            ExchangeHistory exchangeHistory = ExchangeHistory.builder()
                    .reservation(exchangeReservation)
                    .appliedRate(currentRate)
                    .pointHistory(pointHistory)
                    .user(user)
                    .build();
            pointHistoryRepository.save(pointHistory);
            exchangeHistoryRepository.save(exchangeHistory);
        }

        ExchangeReservation saved = exchangeReservationRepository.save(exchangeReservation);

        return ExchangeReservationResponse.from(saved);
    }

    @Transactional
    public ExchangeReservationResponse reserveSell(UserInfo userInfo, ExchangeReservationRequest request) {
        User user = userInfo.user();
        ExchangeRate currentRate = exchangeRateRepository.findTopByBasedateOrderByIdDesc(LocalDate.now())
                .orElse(exchangeRateRepository.findTopByOrderById());

        Wallet withdrawWallet = walletRepository.findByUserAndCurrencyCode(user, request.currencyCode());
        Wallet depositWallet = walletRepository.findByUserAndCurrencyCode(user, CurrencyCode.KRW);
        ExchangeReservation exchangeReservation = request.toEntity(withdrawWallet, depositWallet);

        if(currentRate.getSellCurrency().equals(request.targetRate())) {
            exchangeReservation.complete();

            PointHistory pointHistory = PointHistory.builder()
                    .amount(exchangeReservation.getAfterAmount())
                    .historyType(HistoryType.CHARGE)
                    .user(user)
                    .build();

            ExchangeHistory exchangeHistory = ExchangeHistory.builder()
                    .reservation(exchangeReservation)
                    .appliedRate(currentRate)
                    .pointHistory(pointHistory)
                    .user(user)
                    .build();

            pointHistoryRepository.save(pointHistory);
            exchangeHistoryRepository.save(exchangeHistory);
        }

        ExchangeReservation saved = exchangeReservationRepository.save(exchangeReservation);

        return ExchangeReservationResponse.from(saved);
    }

    // TODO: 2024-08-11 interface로 코드 개선
    @Transactional
    public ExchangeBatchResponse batchBuyExchange(CurrencyCode currencyCode) {
        // 최신 환율 조회하기
        ExchangeRate currentRate = exchangeRateRepository.findTopByBasedateOrderByIdDesc(LocalDate.now())
                .orElse(exchangeRateRepository.findTopByOrderById());

        // 최신 환율을 목표 환율로 설정한 환전 예약 건 조회하기
        List<ExchangeReservation> reservations = exchangeReservationRepository.findByStatusAndTargetRateAndType(PENDING, currentRate.getGetCurrency(), BUY);
        List<PointHistory> pointHistories = new ArrayList<>();
        List<ExchangeHistory> exchangeHistories = new ArrayList<>();

        // 환전 예약 건 상태 complete로 변환
        for (ExchangeReservation r : reservations) {
            log.info("보유 원화: {} => 처리 금액: {}", r.getWithdrawWallet().getBalance(), r.getBeforeAmount());
            r.getWithdrawWallet().usePoint(r.getBeforeAmount());
            r.getDepositWallet().chargePoint(r.getAfterAmount());

            // 포인트 내역 생성
            PointHistory pointHistory = PointHistory.builder()
                    .amount(r.getBeforeAmount())
                    .historyType(HistoryType.COST)
                    .user(r.getWithdrawWallet().getUser())
                    .build();
            pointHistories.add(pointHistory);

            // 환전 내역 생성
            ExchangeHistory exchangeHistory = ExchangeHistory.builder()
                    .user(r.getWithdrawWallet().getUser())
                    .appliedRate(currentRate)
                    .reservation(r)
                    .pointHistory(pointHistory)
                    .build();

            exchangeHistories.add(exchangeHistory);

            r.complete();
        }

        pointHistoryRepository.saveAll(pointHistories);
        exchangeHistoryRepository.saveAll(exchangeHistories);

        return new ExchangeBatchResponse(exchangeHistories.size());

    }

    @Transactional
    public ExchangeBatchResponse batchSellExchange(CurrencyCode currencyCode) {
        // 최신 환율 조회하기
        ExchangeRate currentRate = exchangeRateRepository.findTopByBasedateOrderByIdDesc(LocalDate.now())
                .orElse(exchangeRateRepository.findTopByOrderById());

        // 최신 환율을 목표 환율로 설정한 환전 예약 건 조회하기
        List<ExchangeReservation> reservations = exchangeReservationRepository.findByStatusAndTargetRateAndType(PENDING, currentRate.getGetCurrency(), BUY);
        List<PointHistory> pointHistories = new ArrayList<>();
        List<ExchangeHistory> exchangeHistories = new ArrayList<>();

        // 환전 예약 건 상태 complete로 변환
        for (ExchangeReservation r : reservations) {
            log.info("보유 원화: {} => 처리 금액: {}", r.getWithdrawWallet().getBalance(), r.getBeforeAmount());
            r.getWithdrawWallet().usePoint(r.getBeforeAmount());
            r.getDepositWallet().chargePoint(r.getAfterAmount());

            // 포인트 내역 생성
            PointHistory pointHistory = PointHistory.builder()
                    .amount(r.getAfterAmount())
                    .historyType(HistoryType.CHARGE)
                    .user(r.getWithdrawWallet().getUser())
                    .build();
            pointHistories.add(pointHistory);

            // 환전 내역 생성
            ExchangeHistory exchangeHistory = ExchangeHistory.builder()
                    .user(r.getWithdrawWallet().getUser())
                    .appliedRate(currentRate)
                    .reservation(r)
                    .pointHistory(pointHistory)
                    .build();

            exchangeHistories.add(exchangeHistory);

            r.complete();

            return new ExchangeBatchResponse(exchangeHistories.size());
        }

        pointHistoryRepository.saveAll(pointHistories);
        exchangeHistoryRepository.saveAll(exchangeHistories);

        return new ExchangeBatchResponse(exchangeHistories.size());
    }

}

