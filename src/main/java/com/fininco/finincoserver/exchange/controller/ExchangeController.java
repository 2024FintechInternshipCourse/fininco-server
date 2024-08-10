package com.fininco.finincoserver.exchange.controller;

import com.fininco.finincoserver.exchange.dto.request.ExchangeReservationRequest;
import com.fininco.finincoserver.exchange.dto.response.ExchangeReservationResponse;
import com.fininco.finincoserver.exchange.service.ExchangeService;
import com.fininco.finincoserver.global.auth.Auth;
import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.point.entity.CurrencyCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<ExchangeReservationResponse> reserveExchange(@Auth UserInfo userInfo,
                                                                       @RequestBody ExchangeReservationRequest request) {
        log.info(">> controller: {}", request.targetRate());
        ExchangeReservationResponse response = exchangeService.reserveBuy(userInfo, request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/batch/buy")
    public ResponseEntity<Void> batchBuyExchange(@RequestParam("currency-code") CurrencyCode currencyCode) {

        exchangeService.batchBuyExchange(currencyCode);

        return ResponseEntity.noContent().build();
    }




}
