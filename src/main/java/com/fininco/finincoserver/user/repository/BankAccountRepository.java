package com.fininco.finincoserver.user.repository;

import com.fininco.finincoserver.user.entity.BankAccount;
import com.fininco.finincoserver.user.entity.BankAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, BankAccountInfo> {

    BankAccount findByInfo(BankAccountInfo info);

}
