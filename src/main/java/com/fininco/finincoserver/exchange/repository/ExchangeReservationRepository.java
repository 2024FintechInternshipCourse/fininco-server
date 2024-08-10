package com.fininco.finincoserver.exchange.repository;

import com.fininco.finincoserver.exchange.entity.ExchangeReservation;
import com.fininco.finincoserver.exchange.entity.ExchangeStatus;
import com.fininco.finincoserver.exchange.entity.ExchangeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExchangeReservationRepository extends JpaRepository<ExchangeReservation, Long> {

    List<ExchangeReservation> findByStatusAndTargetRateAndType(ExchangeStatus status, BigDecimal targetRate, ExchangeType type);
}
