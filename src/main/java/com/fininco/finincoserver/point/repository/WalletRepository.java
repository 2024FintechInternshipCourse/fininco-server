package com.fininco.finincoserver.point.repository;

import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUserAndAndCurrencyCode(User user, CurrencyCode currencyCode);

}
