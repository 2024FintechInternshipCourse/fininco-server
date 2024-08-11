package com.fininco.finincoserver.payment.repository;

import com.fininco.finincoserver.payment.entity.Payment;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 사용자의 결제 내역 조회
    List<Payment> findByUser(User user);

    // 엔화, 달러별 결제 내역 조회
    List<Payment> findBywallet(Wallet wallet);

    // 특정 사용자, 특정 지갑에 대한 결제 내역 조회
    List<Payment> findByUserAndWallet(User user, Wallet wallet);
}
