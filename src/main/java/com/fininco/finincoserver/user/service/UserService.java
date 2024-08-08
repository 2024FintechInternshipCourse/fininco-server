package com.fininco.finincoserver.user.service;

import com.fininco.finincoserver.global.auth.UserInfo;
import com.fininco.finincoserver.point.repository.WalletRepository;
import com.fininco.finincoserver.user.dto.request.BankAccountCreateRequest;
import com.fininco.finincoserver.user.dto.response.BankAccountResponse;
import com.fininco.finincoserver.user.entity.BankAccount;
import com.fininco.finincoserver.user.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountResponse createBankAccount(UserInfo userInfo, BankAccountCreateRequest request) {
        BankAccount bankAccount = request.toEntity(userInfo.user());
        BankAccount saved = bankAccountRepository.save(bankAccount);

        return BankAccountResponse.from(saved);
    }

}
