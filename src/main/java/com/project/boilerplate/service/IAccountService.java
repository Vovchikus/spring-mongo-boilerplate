package com.project.boilerplate.service;

import com.project.boilerplate.dto.account.CreateAccountRequest;
import com.project.boilerplate.entity.Account;

import java.security.NoSuchAlgorithmException;

public interface IAccountService {
    Account handleTokenCreation(CreateAccountRequest createAccountRequest) throws NoSuchAlgorithmException;
}
