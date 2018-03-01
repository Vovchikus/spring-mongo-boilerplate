package com.project.home24.repository;

import com.project.home24.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends MongoRepository<Account, Long> {
    Account getAccountByToken(String token);
}
