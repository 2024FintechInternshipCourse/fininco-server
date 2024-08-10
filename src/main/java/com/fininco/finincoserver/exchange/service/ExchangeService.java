package com.fininco.finincoserver.exchange.service;

import com.fininco.finincoserver.exchange.dto.request.ExchangeReservationRequest;
import com.fininco.finincoserver.exchange.dto.response.ExchangeReservationResponse;
import com.fininco.finincoserver.exchange.entity.ExchangeRate;
import com.fininco.finincoserver.exchange.entity.ExchangeReservation;
import com.fininco.finincoserver.exchange.repository.ExchangeRateRepository;
import com.fininco.finincoserver.exchange.repository.ExchangeReservationRepository;
import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.point.repository.WalletRepository;
import com.fininco.finincoserver.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Transactional
    public ExchangeReservationResponse reserveBuy(UserInfo userInfo, ExchangeReservationRequest request) {
        User user = userInfo.user();

        Wallet withdrawWallet = walletRepository.findByUserAndAndCurrencyCode(user, CurrencyCode.KRW);
        Wallet depositWallet = walletRepository.findByUserAndAndCurrencyCode(user, request.currencyCode());
        ExchangeReservation exchangeReservation = request.toEntity(withdrawWallet, depositWallet);

        ExchangeReservation saved = exchangeReservationRepository.save(exchangeReservation);

        return ExchangeReservationResponse.from(saved);
    }

    @Transactional
    public void batchBuyExchange(CurrencyCode currencyCode) {
        // 최신 환율 조회하기
        ExchangeRate currentRate = exchangeRateRepository.findTopByBasedateOrderByIdDesc(LocalDate.now());

        // 최신 환율을 목표 환율로 설정한 환전 예약 건 조회하기
        List<ExchangeReservation> reservations = exchangeReservationRepository.findByStatusAndTargetRateAndType(PENDING, currentRate.getGetCurrency(), BUY);

        // 환전 예약 건 상태 complete로 변환
        for(ExchangeReservation r:reservations) {
            log.info("보유 원화: {} => 처리 금액: {}", r.getWithdrawWallet().getBalance(), r.getBeforeAmount());
            r.getWithdrawWallet().usePoint(r.getBeforeAmount());
            r.getDepositWallet().chargePoint(r.getAfterAmount());
            r.complete();
        }

        // 환전 내역 생성
        // TODO: 2024-08-10 환전 내역 생성

        // 포인트 내역 생성
        // TODO: 2024-08-10 라은 포인트 내역 완성 시 반영

    }
}

