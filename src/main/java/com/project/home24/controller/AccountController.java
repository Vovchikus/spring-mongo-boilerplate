package com.project.home24.controller;

import com.project.home24.dto.account.CreateAccountRequest;
import com.project.home24.dto.account.AccountResponse;
import com.project.home24.entity.Account;
import com.project.home24.exception.AccountNotFoundException;
import com.project.home24.repository.AccountRepository;
import com.project.home24.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @PostMapping(value = "/token")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@Valid @RequestBody CreateAccountRequest createAccountRequest)
            throws NoSuchAlgorithmException {
        Account account = accountService.handleTokenCreation(createAccountRequest);
        AccountResponse response = new AccountResponse();
        response.setToken(account.getToken());
        response.setEmail(account.getEmail());
        return response;
    }

    @GetMapping(value = "/account")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccount(@Valid @RequestHeader(value = "X-Auth-Token") String token)
            throws AccountNotFoundException {
        Optional<Account> opt = Optional.ofNullable(accountRepository.getAccountByToken(token));
        Account account = opt.orElseThrow(() -> new AccountNotFoundException(token));
        AccountResponse response = new AccountResponse();
        response.setEmail(account.getEmail());
        response.setToken(account.getToken());
        return response;
    }
}
