package com.project.boilerplate.repository;

import com.project.boilerplate.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
    Account getAccountByToken(String token);
}
