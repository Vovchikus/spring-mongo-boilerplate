package com.project.home24.service;

import com.project.home24.dto.account.CreateAccountRequest;
import com.project.home24.entity.Account;

import java.security.NoSuchAlgorithmException;

public interface IAccountService {
    Account handleTokenCreation(CreateAccountRequest createAccountRequest) throws NoSuchAlgorithmException;
}
