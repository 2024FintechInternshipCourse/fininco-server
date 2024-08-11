package com.fininco.finincoserver.exchange.batch;

import com.fininco.finincoserver.exchange.entity.ExchangeHistory;
import com.fininco.finincoserver.exchange.entity.ExchangeRate;
import com.fininco.finincoserver.exchange.entity.ExchangeReservation;
import com.fininco.finincoserver.exchange.repository.ExchangeHistoryRepository;
import com.fininco.finincoserver.exchange.repository.ExchangeRateRepository;
import com.fininco.finincoserver.exchange.repository.ExchangeReservationRepository;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.HistoryType;
import com.fininco.finincoserver.point.entity.PointHistory;
import com.fininco.finincoserver.point.repository.PointHistoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fininco.finincoserver.exchange.entity.ExchangeStatus.PENDING;
import static com.fininco.finincoserver.exchange.entity.ExchangeType.BUY;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeBuyTasklet implements Tasklet {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeReservationRepository exchangeReservationRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final ExchangeHistoryRepository exchangeHistoryRepository;

    @Override
    @Transactional
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // 최신 환율 조회하기
        Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();
        String currencyCode = (String) jobParameters.get("currencyCode");
        log.info(">>>>>> currencyCodeString: {}", currencyCode);


        // 최신 환율 조회
        ExchangeRate currentRate = new ExchangeRate();
        try {
            currentRate = exchangeRateRepository.findTopByBasedateAndCurrencyCodeOrderByIdDesc(LocalDate.now(), currencyCode)
                    .orElse(exchangeRateRepository.findTopByCurrencyCodeOrderById(currencyCode).orElseThrow(EntityNotFoundException::new));
        } catch (EntityNotFoundException exception) {
            log.info("최신 환율 정보가 없습니다.");
        }

        // 최신 환율을 목표 환율로 설정한 환전 예약 건 조회하기
        List<ExchangeReservation> reservations = exchangeReservationRepository.findByStatusAndTargetRateAndType(PENDING, currentRate.getGetCurrency(), BUY);
        List<PointHistory> pointHistories = new ArrayList<>();
        List<ExchangeHistory> exchangeHistories = new ArrayList<>();

        // 환전 예약 건 상태 complete로 변환
        for (ExchangeReservation r : reservations) {
            r.getWithdrawWallet().usePoint(r.getBeforeAmount());
            r.getDepositWallet().chargePoint(r.getAfterAmount());

            // 포인트 내역 생성
            PointHistory pointHistory = PointHistory.builder()
                    .amount(r.getAfterAmount())
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


        return RepeatStatus.FINISHED;
    }
}
