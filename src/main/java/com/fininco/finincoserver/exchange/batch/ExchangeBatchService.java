package com.fininco.finincoserver.exchange.batch;

import com.fininco.finincoserver.point.entity.CurrencyCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeBatchService {
    private final ExchangeJobLauncherRunner jobLauncherRunner;

    @Bean
    @Scheduled(cron = "0 */1 * * * *")
    public void startBatch() {

        for (CurrencyCode code : CurrencyCode.getForeignCurrencyCodes()) {
            String currencyCode = code.name();
            log.info(">>> currencyCode: {} = {}", currencyCode, LocalDateTime.now());
            try {
                // 배치 작업 실행
                jobLauncherRunner.runJob(currencyCode);
                log.info(">>> 배치 실행 완료: {}", currencyCode);
            } catch (Exception e) {
                log.info(">>> 배치 실행 실패: {}", currencyCode);
            }
        }
    }
}
