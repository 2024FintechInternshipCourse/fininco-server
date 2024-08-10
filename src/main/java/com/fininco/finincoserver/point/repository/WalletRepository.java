package com.fininco.finincoserver.point.repository;

import com.fininco.finincoserver.point.entity.CurrencyCode;
import com.fininco.finincoserver.point.entity.Wallet;
import com.fininco.finincoserver.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUserAndCurrencyCode(User user, CurrencyCode currencyCode);

    List<Wallet> findByCurrencyCodeAndUserIn(CurrencyCode currencyCode, List<User> users);
}
