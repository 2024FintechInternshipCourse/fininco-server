package com.fininco.finincoserver.exchange.controller;

import com.fininco.finincoserver.exchange.dto.ExchangeRateResponseDto;
import com.fininco.finincoserver.exchange.service.ExchangeRateService;
import com.fininco.finincoserver.global.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * 주어진 날짜의 환율 정보 GET http://localhost:8080/exchange/rates/today
     *
     * @return 환율 정보를 담은 ResponseEntity 객체
     */

    @GetMapping("/rates/today")
    public ResponseEntity<List<ExchangeRateResponseDto>> getTodayExchangeRate() {
        try {
            // 오늘 날짜를 기준으로 환율 데이터를 가져옴
            LocalDate today = DateUtils.getLastBusinessDay(LocalDate.now());
            List<ExchangeRateResponseDto> response = exchangeRateService.getExchangeRate(today.toString().replace("-", ""));
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            // 에러 처리
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * 어제 날짜 환율 정보를 GET http://localhost:8080/exchange/rates/yesterday
     *
     * @return 어제 환율 정보를 담은 ResponseEntity 객체
     */

    @GetMapping("/rates/yesterday")
    public ResponseEntity<List<ExchangeRateResponseDto>> getYesterdayExchangeRate() {
        try {
            // 오늘 날짜를 기준으로 어제 날짜를 계산
            LocalDate today = DateUtils.getLastBusinessDay(LocalDate.now());
            LocalDate yesterday = DateUtils.getPreviousBusinessDay(today);
            List<ExchangeRateResponseDto> response = exchangeRateService.getExchangeRate(yesterday.toString().replace("-", ""));
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            // 에러 처리
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}

