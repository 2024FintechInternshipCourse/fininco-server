package com.fininco.finincoserver.exchange.repository;

import com.fininco.finincoserver.exchange.entity.ExchangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistory, Long> {
}
