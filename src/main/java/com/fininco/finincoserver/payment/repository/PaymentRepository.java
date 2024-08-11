package com.fininco.finincoserver.payment.repository;

import com.fininco.finincoserver.payment.entity.Payment;
import com.fininco.finincoserver.point.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // 엔화, 달러별 결제 내역 조회
    List<Payment> findByWallet(Wallet wallet);
}
