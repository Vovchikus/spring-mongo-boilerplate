package com.project.boilerplate.controller;

import com.project.boilerplate.dto.account.AccountResponse;
import com.project.boilerplate.dto.account.CreateAccountRequest;
import com.project.boilerplate.entity.Account;
import com.project.boilerplate.exception.AccountNotFoundException;
import com.project.boilerplate.repository.AccountRepository;
import com.project.boilerplate.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @PostMapping(value = "/login")
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
        Account account = Optional.ofNullable(accountRepository.getAccountByToken(token))
                .orElseThrow(() -> new AccountNotFoundException(token));
        AccountResponse response = new AccountResponse();
        response.setEmail(account.getEmail());
        response.setToken(account.getToken());
        return response;
    }
}
