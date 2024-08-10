package com.fininco.finincoserver.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fininco.finincoserver.exchange.entity.ExchangeRate;

import java.time.LocalDate;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>{

    ExchangeRate findTopByBasedateOrderByIdDesc(LocalDate date);
}
