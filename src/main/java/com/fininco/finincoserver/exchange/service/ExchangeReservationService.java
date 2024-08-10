package com.fininco.finincoserver.exchange.service;

import com.fininco.finincoserver.exchange.dto.request.ExchangeReservationRequest;
import com.fininco.finincoserver.exchange.dto.response.ExchangeReservationResponse;
import com.fininco.finincoserver.exchange.entity.ExchangeReservation;
import com.fininco.finincoserver.exchange.repository.ExchangeReservationRepository;
import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExchangeReservationService {

    private final ExchangeReservationRepository exchangeReservationRepository;

    @Transactional
    public ExchangeReservationResponse reserveExchange(UserInfo userInfo, ExchangeReservationRequest request) {
        User user = userInfo.user();
        ExchangeReservation exchangeReservation = request.toEntity(user);

        ExchangeReservation saved = exchangeReservationRepository.save(exchangeReservation);

        ExchangeReservationResponse response = ExchangeReservationResponse.from(saved);

        return response;
    }

}

