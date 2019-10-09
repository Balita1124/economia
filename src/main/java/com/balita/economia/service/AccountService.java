package com.balita.economia.service;


import com.balita.economia.model.Account;
import com.balita.economia.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;


    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
}
