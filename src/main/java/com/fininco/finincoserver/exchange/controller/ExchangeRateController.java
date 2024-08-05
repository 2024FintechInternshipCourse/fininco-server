package com.fininco.finincoserver.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fininco.finincoserver.exchange.dto.ExchangeRateResponseDto;
import com.fininco.finincoserver.exchange.service.ExchangeRateService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/exchange")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * 주어진 날짜의 환율 정보 GET http://localhost:8080/exchange/rates?searchdate=20240805
     * @param searchdate 요청 날짜 (형식: YYYYMMDD)
     * @return 환율 정보를 담은 ResponseEntity 객체
     */
    
    @GetMapping("/rates")
    public ResponseEntity<List<ExchangeRateResponseDto>> getExchangeRate(@RequestParam(name = "searchdate") String searchdate) {
        try {
            List<ExchangeRateResponseDto> response = exchangeRateService.getExchangeRate(searchdate);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            // 에러 처리
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}

