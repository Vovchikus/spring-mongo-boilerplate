package com.project.boilerplate.service;

import com.project.boilerplate.dto.account.CreateAccountRequest;
import com.project.boilerplate.entity.Account;
import com.project.boilerplate.repository.AccountRepository;
import com.project.boilerplate.util.HashingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account handleTokenCreation(CreateAccountRequest createAccountRequest) throws NoSuchAlgorithmException {
        Account account = new Account();
        account.setEmail(createAccountRequest.getEmail());
        account.setPassword(HashingUtils.encryptPassword(createAccountRequest.getPassword()));
        account.setToken(HashingUtils.generateSHA256AuthToken(
                createAccountRequest.getEmail(),
                createAccountRequest.getPassword())
        );
        return accountRepository.insert(account);
    }
}
