package com.fininco.finincoserver.exchange.controller;

import com.fininco.finincoserver.exchange.dto.request.ExchangeReservationRequest;
import com.fininco.finincoserver.exchange.dto.response.ExchangeReservationResponse;
import com.fininco.finincoserver.exchange.entity.ExchangeType;
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
    public ResponseEntity<ExchangeReservationResponse> reserveExchangeBuy(@Auth UserInfo userInfo,
                                                                          @RequestBody ExchangeReservationRequest request) {
        
        if (request.type().equals(ExchangeType.BUY)) {
            return ResponseEntity.ok(exchangeService.reserveBuy(userInfo, request));
        } else if (request.type().equals(ExchangeType.SELL)) {
            return ResponseEntity.ok(exchangeService.reserveSell(userInfo, request));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> batchExchange(
            @RequestParam("type") ExchangeType type,
            @RequestParam("code") CurrencyCode currencyCode) {

        if(type.equals(ExchangeType.BUY)){
            ResponseEntity.ok(exchangeService.batchBuyExchange(currencyCode));
        } else if (type.equals(ExchangeType.SELL)) {
            ResponseEntity.ok(exchangeService.batchSellExchange(currencyCode));
        }

        return ResponseEntity.badRequest().build();
    }

}
