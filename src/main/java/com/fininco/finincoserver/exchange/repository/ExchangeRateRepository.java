package com.fininco.finincoserver.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fininco.finincoserver.exchange.entity.ExchangeRate;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>{

    Optional<ExchangeRate> findTopByBasedateOrderByIdDesc(LocalDate date);
    ExchangeRate findTopByOrderById();
}
