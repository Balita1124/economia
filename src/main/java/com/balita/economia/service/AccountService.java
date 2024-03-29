package com.balita.economia.service;


import com.balita.economia.model.Account;
import com.balita.economia.model.Partner;
import com.balita.economia.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;


    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    public List<Account> findAccounts() {
        return accountRepository.findAll();
    }

    public Account makePayment(Account account, Double amount) {
        account.setAmount(account.getAmount() + amount);
        return account;
    }

    public Account makeWithdrawal(Account account, Double amount) {
        account.setAmount(account.getAmount() - amount);
        return account;
    }
}
