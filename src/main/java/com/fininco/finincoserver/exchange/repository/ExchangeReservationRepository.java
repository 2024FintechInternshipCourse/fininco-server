package com.fininco.finincoserver.exchange.repository;

import com.fininco.finincoserver.exchange.entity.ExchangeReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeReservationRepository extends JpaRepository<ExchangeReservation, Long> {

}
