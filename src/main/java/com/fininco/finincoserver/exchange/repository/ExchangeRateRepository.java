package com.fininco.finincoserver.exchange.repository;

import com.fininco.finincoserver.point.entity.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fininco.finincoserver.exchange.entity.ExchangeRate;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>{

    Optional<ExchangeRate> findTopByBasedateAndCurrencyCodeOrderByIdDesc(LocalDate date, String currencyCode);
    Optional<ExchangeRate> findTopByCurrencyCodeOrderById(String currencyCode);
}
