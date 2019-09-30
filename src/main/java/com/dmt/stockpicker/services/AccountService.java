package com.dmt.stockpicker.services;

import com.dmt.stockpicker.model.Account;
import com.dmt.stockpicker.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account account){
        return accountRepository.save(account);
    }

    // TODO: remove after security is added
    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }
}
